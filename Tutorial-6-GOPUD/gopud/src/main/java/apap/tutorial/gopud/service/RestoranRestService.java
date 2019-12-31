package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.RestoranModel;

import java.util.List;

public interface RestoranRestService {

    RestoranModel createRestoran(RestoranModel restoran);

    List<RestoranModel> retrieveListRestoran();

    RestoranModel getRestoranByIdRestoran(Long idRestoran);

    RestoranModel changeRestoran(Long idRestoran, RestoranModel restoranUpdate);

    void deleteRestoran(Long idRestoran);
}
