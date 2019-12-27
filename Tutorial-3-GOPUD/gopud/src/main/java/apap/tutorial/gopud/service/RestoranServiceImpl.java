package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.repository.RestoranDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestoranServiceImpl implements RestoranService {

    @Autowired
    private RestoranDb restoranDb;

    @Override
    public void addRestoran(RestoranModel restoran) {
        restoranDb.save(restoran);
    }

    @Override
    public List<RestoranModel> getRestoranList() {
        return restoranDb.findAll();
    }

    @Override
    public Optional<RestoranModel> getRestoranByIdRestoran(Long idRestoran) {
        return restoranDb.findByIdRestoran(idRestoran);
    }

    @Override
    public RestoranModel changeRestoran(RestoranModel restoranModel) {
        Optional<RestoranModel> targetRestoranOpt = restoranDb.findById(restoranModel.getIdRestoran());

        if (targetRestoranOpt.isPresent()) {
            RestoranModel targetRestoran = targetRestoranOpt.get();
            targetRestoran.setNama(restoranModel.getNama());
            targetRestoran.setAlamat(restoranModel.getAlamat());
            targetRestoran.setNomorTelepon(restoranModel.getNomorTelepon());
            restoranDb.save(targetRestoran);
            return targetRestoran;
        } else {
            return null;
        }
    }
}
