package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.RestoranModel;

import java.util.List;

public interface RestoranService {

    void addRestoran(RestoranModel restoran);

    List<RestoranModel> getRestoranList();

    RestoranModel getRestoranByIdRestoran(String idRestoran);
}
