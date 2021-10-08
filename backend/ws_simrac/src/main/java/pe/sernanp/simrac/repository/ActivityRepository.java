package pe.sernanp.simrac.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.simrac.model.ActivityModel;


public interface ActivityRepository extends JpaRepository<ActivityModel, Integer> {
	

	
}
