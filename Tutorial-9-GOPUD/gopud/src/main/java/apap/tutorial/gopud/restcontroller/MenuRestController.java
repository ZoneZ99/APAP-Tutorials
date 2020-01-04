package apap.tutorial.gopud.restcontroller;

import apap.tutorial.gopud.dto.MenuDto;
import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.service.MenuRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class MenuRestController {

    @Autowired
    private MenuRestService menuRestService;

    @PostMapping(value = "/menu")
    private MenuModel createMenu(
            @Valid
            @RequestBody
                    MenuModel menu,
            BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Request body has invalid type or missing field"
            );
        } else {
            return menuRestService.createMenu(menu);
        }
    }

    @PutMapping(value = "/menu/{idMenu}")
    private MenuModel updateMenu(
            @PathVariable(value = "idMenu")
                    Long idMenu,
            @RequestBody
                    MenuModel menu) {

        try {
            return menuRestService.changeMenu(idMenu, menu);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Menu with " + idMenu + " not found"
            );
        }
    }

    @GetMapping(value = "/menu/{idMenu}")
    private MenuDto getOneMenu(@PathVariable(value = "idMenu") Long idMenu) {
        try {
            MenuModel menuEntity = menuRestService.getMenuByIdMenu(idMenu);
            return convertMenuEntityToDto(menuEntity);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Menu with " + idMenu + " not found"
            );
        }
    }

    @GetMapping(value = "/menus")
    private List<MenuDto> getAllMenus() {
        List<MenuModel> menuEntityList = menuRestService.getAllMenus();
        List<MenuDto> menuDtoList = new ArrayList<>();
        for (MenuModel menuEntity : menuEntityList) {
            MenuDto menuDto = convertMenuEntityToDto(menuEntity);
            menuDtoList.add(menuDto);
        }
        return menuDtoList;
    }

    @DeleteMapping(value = "/menu/{idMenu}")
    private ResponseEntity<String> deleteMenu(@PathVariable(value = "idMenu") Long idMenu) {
        try {
            menuRestService.deleteMenu(idMenu);
            return ResponseEntity.ok("Menu with ID " + idMenu + " has been deleted");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Menu with ID " + idMenu + " not found"
            );
        }
    }

    private MenuDto convertMenuEntityToDto(MenuModel menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setNama(menu.getNama());
        menuDto.setDeskripsi(menu.getDeskripsi());
        menuDto.setHarga(menu.getHarga());
        menuDto.setDurasiMasak(menu.getDurasiMasak());
        menuDto.setRestoran(menu.getRestoran().getIdRestoran());
        return menuDto;
    }
}
