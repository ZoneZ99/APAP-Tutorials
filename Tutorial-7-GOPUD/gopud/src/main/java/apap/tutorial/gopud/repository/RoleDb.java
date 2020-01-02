package apap.tutorial.gopud.repository;

import apap.tutorial.gopud.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDb extends JpaRepository<RoleModel, Long> {

}
