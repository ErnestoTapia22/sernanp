package pe.sernanp.simrac.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.ActivityModel;


public interface ActivityRepository extends JpaRepository<ActivityModel, Integer> {
	

	@Query(value="select * from simrac.t_actividad WHERE 1=1", nativeQuery=true)
    List<ActivityModel> searchByMonitoringAndAgreement(@Param("id") int id);
}
