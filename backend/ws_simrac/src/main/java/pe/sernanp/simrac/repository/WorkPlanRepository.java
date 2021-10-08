package pe.sernanp.simrac.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.WorkPlanModel;

public interface WorkPlanRepository extends JpaRepository<WorkPlanModel, Integer>{

	@Query(value="select * from simrac.t_plan_trabajo WHERE int_acuerdoid=:id", nativeQuery=true)
	List<WorkPlanModel> searchByAgreement(@Param("id") int id);
	
}