package pe.sernanp.simrac.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.ActivityModel;


public interface ActivityRepository extends JpaRepository<ActivityModel, Integer> {
	
		
	@Query(value="select a.* from simrac.t_actividad as a "
			+ "	inner join simrac.t_plan_trabajo as pt on pt.srl_id = a.int_plantrabajoid and pt.bol_activo = true "
			+ "	where pt.int_acuerdoid=:pagreementid "
			+ "	order by a.srl_id", nativeQuery=true)
    List<ActivityModel> searchByAgreement(@Param("pagreementid") int id);
}
