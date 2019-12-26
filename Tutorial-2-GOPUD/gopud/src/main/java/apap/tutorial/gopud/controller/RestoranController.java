package apap.tutorial.gopud.controller;

import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.service.RestoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RestoranController {

    @Autowired
    private RestoranService restoranService;

    @RequestMapping("/restoran/add")
    public String add(
            @RequestParam(value = "idRestoran")
                String idRestoran,
            @RequestParam(value = "nama")
                String nama,
            @RequestParam(value = "alamat")
                String alamat,
            @RequestParam(value = "nomorTelepon")
                Integer nomorTelepon,
            Model model) {

        RestoranModel restoran = new RestoranModel(idRestoran, nama, alamat, nomorTelepon);
        restoranService.addRestoran(restoran);
        model.addAttribute("namaResto", nama);
        return "add-restoran";
    }

    @RequestMapping("/restoran/view")
    public String view(
            @RequestParam(value = "idRestoran")
                String idRestoran,
            Model model) {

        RestoranModel restoran = restoranService.getRestoranByIdRestoran(idRestoran);
        model.addAttribute("resto", restoran);
        return "view-restoran";
    }

    @RequestMapping("/restoran/viewall")
    public String viewall(Model model) {
        List<RestoranModel> listRestoran = restoranService.getRestoranList();
        model.addAttribute("restoList", listRestoran);
        return "viewall-restoran";
    }
}
