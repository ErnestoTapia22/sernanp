package pe.sernanp.simrac.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.WorkPlanModel;

public interface WorkPlanRepository extends JpaRepository<WorkPlanModel, Integer>{

	@Query(value="select * from simrac.t_plan_trabajo WHERE int_acuerdoid=:id", nativeQuery=true)
	List<WorkPlanModel> searchByAgreement(@Param("id") int id);
		
	@Modifying
	@Query(value="update simrac.t_plan_trabajo set bol_activo = false where int_acuerdoid = :pagreementeid", nativeQuery=true)
    int updatePlanActive(@Param("pagreementeid") int id);
}