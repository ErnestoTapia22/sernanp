package pe.sernanp.simrac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.simrac.model.PersonaModel;

public interface PersonaRepository extends JpaRepository<PersonaModel, Long> {

}
