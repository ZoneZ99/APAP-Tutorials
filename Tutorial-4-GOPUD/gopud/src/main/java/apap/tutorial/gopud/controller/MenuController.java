package apap.tutorial.gopud.controller;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.service.MenuService;
import apap.tutorial.gopud.service.RestoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MenuController {

    @Autowired
    MenuService menuService;

    @Qualifier(value = "restoranServiceImpl")
    @Autowired
    RestoranService restoranService;

    @RequestMapping(value = "/menu/add/{idRestoran}", method = RequestMethod.GET)
    private String addProductFormPage(
            @PathVariable(value = "idRestoran")
                    Long idRestoran,
            Model model) {

        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).get();
        List<MenuModel> menuList = new ArrayList<>();
        menuList.add(new MenuModel());
        restoran.setListMenu(menuList);
        model.addAttribute("resto", restoran);
        return "form-add-menu";
    }

    @RequestMapping(value = "/menu/add/{idRestoran}", params = {"save"}, method = RequestMethod.POST)
    private String addProductSubmit(
            @ModelAttribute
                    RestoranModel restoran,
            Model model) {

        RestoranModel currentRestoran = restoranService.getRestoranByIdRestoran(restoran.getIdRestoran()).get();
        List<MenuModel> menuList = restoran.getListMenu();
        for (MenuModel menu : menuList) {
            menu.setRestoran(currentRestoran);
            menuService.addMenu(menu);
        }
        return "add-menu";
    }

    @RequestMapping(value = "/menu/add/{idRestoran}", params = {"addRow"}, method = RequestMethod.POST)
    private String addRow(
            @ModelAttribute
                    RestoranModel restoran,
            Model model) {

        if (restoran.getListMenu() == null || restoran.getListMenu().size() == 0) {
            restoran.setListMenu(new ArrayList<>());
        }
        restoran.getListMenu().add(new MenuModel());
        model.addAttribute("resto", restoran);
        return "form-add-menu";
    }

    @RequestMapping(value = "/menu/add/{idRestoran}", params = {"deleteRow"}, method = RequestMethod.POST)
    private String deleteRow(
            @ModelAttribute
                    RestoranModel restoran,
            final HttpServletRequest request,
            Model model) {

        final Integer rowId = Integer.valueOf(request.getParameter("deleteRow"));
        restoran.getListMenu().remove(rowId.intValue());
        model.addAttribute("resto", restoran);
        return "form-add-menu";
    }

    @RequestMapping(value = "/menu/change/{idMenu}", method = RequestMethod.GET)
    private String changeMenuFormPage(
            @PathVariable(value = "idMenu")
                    Long idMenu,
            Model model) {

        MenuModel existingMenu = menuService.findById(idMenu).orElse(null);
        model.addAttribute("menu", existingMenu);
        return "form-change-menu";
    }

    @RequestMapping(value = "/menu/change/{idMenu}", method = RequestMethod.POST)
    private String changeMenuFormSubmit(
            @PathVariable(value = "idMenu")
                    Long idMenu,
            @ModelAttribute
                    MenuModel menu) {

        MenuModel newMenuData = menuService.changeMenu(menu);
        return "change-menu";
    }

    @RequestMapping(value = "/menu/delete/{menuId}", method = RequestMethod.POST)
    public String deleteMenu(
            @PathVariable(value = "menuId")
                    Long menuId,
            Model model) {

        Optional<MenuModel> targetMenuOpt = menuService.findById(menuId);
        MenuModel targetMenu = null;
        if (targetMenuOpt.isPresent()) {
            targetMenu = targetMenuOpt.get();
            menuService.deleteById(targetMenu.getId());
        }
        model.addAttribute("menu", targetMenu);
        return "delete-menu";
    }

    @RequestMapping(value = "/menu/delete", method = RequestMethod.POST)
    private String deleteAllMenu(
            @ModelAttribute
                    RestoranModel restoran,
            Model model) {

        for (MenuModel menu : restoran.getListMenu()) {
            menuService.deleteById(menu.getId());
        }
        return "delete-menu-batch";
    }
}
