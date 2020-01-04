package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.RestoranModel;

import java.util.List;
import java.util.Optional;

public interface RestoranService {

    void addRestoran(RestoranModel restoran);

    List<RestoranModel> getRestoranList();

    Optional<RestoranModel> getRestoranByIdRestoran(Long idRestoran);

    RestoranModel changeRestoran(RestoranModel restoranModel);

    void deleteByIdRestoran(Long idRestoran);
}
