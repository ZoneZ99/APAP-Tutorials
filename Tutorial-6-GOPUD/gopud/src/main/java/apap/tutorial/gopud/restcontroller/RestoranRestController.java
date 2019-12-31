package apap.tutorial.gopud.restcontroller;

import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.service.RestoranRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class RestoranRestController {

    @Autowired
    private RestoranRestService restoranRestService;

    @PostMapping(value = "/restoran")
    private RestoranModel createRestoran(
            @Valid
            @RequestBody
                    RestoranModel restoran,
            BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Request body has invalid type or missing field"
            );
        } else {
            return restoranRestService.createRestoran(restoran);
        }
    }

    @GetMapping(value = "/restoran/{idRestoran}")
    private RestoranModel retrieveRestoran(@PathVariable(value = "idRestoran") Long idRestoran) {
        try {
            return restoranRestService.getRestoranByIdRestoran(idRestoran);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "ID Restoran " + idRestoran + " Not Found"
            );
        }
    }

    @DeleteMapping(value = "/restoran/{idRestoran}")
    private ResponseEntity<String> deleteRestoran(@PathVariable(value = "idRestoran") Long idRestoran) {
        try {
            restoranRestService.deleteRestoran(idRestoran);
            return ResponseEntity.ok("Restoran with ID " + idRestoran + " Deleted");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Restoran with ID " + idRestoran + " Not Found"
            );
        } catch (UnsupportedOperationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Restoran still has menus, please delete the menu first"
            );
        }
    }

    @PutMapping(value = "/restoran/{idRestoran}")
    private RestoranModel updateRestoran(
            @PathVariable(value = "idRestoran")
                    Long idRestoran,
            @RequestBody
                    RestoranModel restoran) {

        try {
            return restoranRestService.changeRestoran(idRestoran, restoran);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "ID Restoran " + idRestoran + " Not Found"
            );
        }
    }

    @GetMapping(value = "/restorans")
    private List<RestoranModel> retrieveListRestoran() {
        return restoranRestService.retrieveListRestoran();
    }
}
