package pe.sernanp.simrac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.simrac.model.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

}
