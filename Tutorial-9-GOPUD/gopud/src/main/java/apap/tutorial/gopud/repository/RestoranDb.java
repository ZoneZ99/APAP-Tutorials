package apap.tutorial.gopud.repository;

import apap.tutorial.gopud.model.RestoranModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestoranDb extends JpaRepository<RestoranModel, Long> {

    Optional<RestoranModel> findByIdRestoran(Long idRestoran);

    void deleteByIdRestoran(Long idRestoran);

    List<RestoranModel> findAllByOrderByNamaAsc();
}
