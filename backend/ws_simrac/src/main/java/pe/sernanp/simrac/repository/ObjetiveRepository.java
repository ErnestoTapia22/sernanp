package pe.sernanp.simrac.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.simrac.model.ObjetiveModel;

public interface ObjetiveRepository extends JpaRepository<ObjetiveModel, Integer>{
	
}