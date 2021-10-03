package pe.sernanp.simrac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.simrac.model.ComponentModel;

public interface ComponentRepository extends JpaRepository<ComponentModel, Integer> {

}