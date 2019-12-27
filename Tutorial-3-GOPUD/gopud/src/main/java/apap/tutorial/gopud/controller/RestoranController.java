package apap.tutorial.gopud.controller;

import apap.tutorial.gopud.model.MenuModel;
import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.service.MenuService;
import apap.tutorial.gopud.service.RestoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class RestoranController {

    @Qualifier(value = "restoranServiceImpl")
    @Autowired
    private RestoranService restoranService;

    @Autowired
    private MenuService menuService;

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/restoran/add", method = RequestMethod.GET)
    public String addRestoranFormPage(Model model) {
        RestoranModel newRestoran = new RestoranModel();
        model.addAttribute("restoran", newRestoran);
        return "form-add-restoran";
    }

    @RequestMapping(value = "/restoran/add", method = RequestMethod.POST)
    public String addRestoranSubmit(
            @ModelAttribute
                    RestoranModel restoran,
            Model model) {

        restoranService.addRestoran(restoran);
        model.addAttribute("namaResto", restoran.getNama());
        return "add-restoran";
    }

    @RequestMapping(value = "/restoran/view", method = RequestMethod.GET)
    public String view(
            @RequestParam(value = "idRestoran")
                    Long idRestoran,
            Model model) {

        Optional<RestoranModel> restoranOpt = restoranService.getRestoranByIdRestoran(idRestoran);
        RestoranModel restoran = null;
        if (restoranOpt.isPresent()) {
            restoran = restoranOpt.get();
            List<MenuModel> menuList = menuService.findAllMenuByIdRestoran(restoran.getIdRestoran());
            model.addAttribute("menuList", menuList);
        }
        model.addAttribute("resto", restoran);
        return "view-restoran";
    }

    @RequestMapping(value = "/restoran/change/{idRestoran}", method = RequestMethod.GET)
    public String changeRestoranFormPage(
            @PathVariable(value = "idRestoran")
                    Long idRestoran,
            Model model) {

        RestoranModel existingRestoran = restoranService.getRestoranByIdRestoran(idRestoran).orElse(null);
        model.addAttribute("restoran", existingRestoran);
        return "form-change-restoran";
    }

    @RequestMapping(value = "/restoran/change/{idRestoran}", method = RequestMethod.POST)
    public String changeRestoranFormSubmit(
            @PathVariable(value = "idRestoran")
                    Long idRestoran,
            @ModelAttribute
                    RestoranModel restoran,
            Model model) {

        RestoranModel newRestoranData = restoranService.changeRestoran(restoran);
        model.addAttribute("restoran", newRestoranData);
        return "change-restoran";
    }

    @RequestMapping(value = "/restoran/view-all", method = RequestMethod.GET)
    public String viewAllRestoran(Model model) {
        List<RestoranModel> allRestoran = restoranService.getRestoranList();
        allRestoran.sort(Comparator.comparing(RestoranModel::getNama));
        model.addAttribute("restoList", allRestoran);
        return "viewall-restoran";
    }

    @RequestMapping(value = "/restoran/delete/{idRestoran}", method = RequestMethod.POST)
    public String deleteRestoran(
        @PathVariable(value = "idRestoran")
                Long idRestoran,
        Model model) {

        Optional<RestoranModel> targetRestoranOpt = restoranService.getRestoranByIdRestoran(idRestoran);
        RestoranModel targetRestoran = null;
        if (targetRestoranOpt.isPresent()) {
            targetRestoran = targetRestoranOpt.get();
            restoranService.deleteByIdRestoran(targetRestoran.getIdRestoran());
        }
        model.addAttribute("restoran", targetRestoran);
        return "delete-restoran";
    }
}
