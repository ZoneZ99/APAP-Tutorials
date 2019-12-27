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

        MenuModel menu = new MenuModel();
        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran).orElse(null);
        menu.setRestoran(restoran);
        model.addAttribute("menu", menu);
        return "form-add-menu";
    }

    @RequestMapping(value = "/menu/add", method = RequestMethod.POST)
    private String addProductSubmit(
            @ModelAttribute
                    MenuModel menu,
            Model model) {

        menuService.addMenu(menu);
        model.addAttribute("nama", menu.getNama());
        return "add-menu";
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
}
