package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.RestoranModel;
import apap.tutorial.gopud.repository.RestoranDb;
import apap.tutorial.gopud.rest.RestoranDetail;
import apap.tutorial.gopud.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RestoranRestServiceImpl implements RestoranRestService {

    private final WebClient webClient;

    @Autowired
    private RestoranDb restoranDb;

    public RestoranRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.restoranUrl).build();
    }

    @Override
    public Mono<String> getStatus(Long idRestoran) {
        return this.webClient.get().uri("/rest/restoran/" + idRestoran + "/status/")
                .retrieve().bodyToMono(String.class);
    }

    @Override
    public Mono<RestoranDetail> postStatus() {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("alamat", "Jalan Akses UI No 2");
        data.add("nomorTelepon", "028102810");
        return this.webClient.post().uri("/rest/restoran/full")
                .syncBody(data)
                .retrieve()
                .bodyToMono(RestoranDetail.class);
    }

    @Override
    public RestoranModel createRestoran(RestoranModel restoran) {
        return restoranDb.save(restoran);
    }

    @Override
    public List<RestoranModel> retrieveListRestoran() {
        return restoranDb.findAllByOrderByNamaAsc();
    }

    @Override
    public RestoranModel getRestoranByIdRestoran(Long idRestoran) {
        Optional<RestoranModel> restoran = restoranDb.findByIdRestoran(idRestoran);
        if (restoran.isPresent()) {
            return restoran.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public RestoranModel changeRestoran(Long idRestoran, RestoranModel restoranUpdate) {
        RestoranModel restoran = getRestoranByIdRestoran(idRestoran);
        restoran.setNama(restoranUpdate.getNama());
        restoran.setAlamat(restoranUpdate.getAlamat());
        restoran.setNomorTelepon(restoranUpdate.getNomorTelepon());
        restoran.setRating(restoranUpdate.getRating());
        return restoranDb.save(restoran);
    }

    @Override
    public void deleteRestoran(Long idRestoran) {
        RestoranModel restoran = getRestoranByIdRestoran(idRestoran);
        if (restoran.getListMenu().isEmpty()) {
            restoranDb.delete(restoran);
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
