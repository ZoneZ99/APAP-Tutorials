package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.RestoranModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestoranInMemoryService implements RestoranService {

    private List<RestoranModel> listRestoran;

    public RestoranInMemoryService() {
        listRestoran = new ArrayList<>();
    }

    @Override
    public void addRestoran(RestoranModel restoran) {
        listRestoran.add(restoran);
    }

    @Override
    public List<RestoranModel> getRestoranList() {
        return listRestoran;
    }

    @Override
    public RestoranModel getRestoranByIdRestoran(String idRestoran) {
        Optional<RestoranModel> returnedRestoran = Optional.empty();
        for (RestoranModel restoran : listRestoran) {
            if (restoran.getIdRestoran().equals(idRestoran)) {
                returnedRestoran = Optional.of(restoran);
            }
        }
        return returnedRestoran.orElse(null);
    }
}
