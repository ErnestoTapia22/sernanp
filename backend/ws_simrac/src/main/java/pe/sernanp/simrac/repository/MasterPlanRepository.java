package pe.sernanp.simrac.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.MasterPlanModel;

public interface MasterPlanRepository extends JpaRepository<MasterPlanModel, Integer>{

	@Query(value="update t_plan_maestro u set u.bol_activo = false where u.int_anpid = :panpid", nativeQuery=true)
    void updatePlanActive(@Param("panpid") int id);
}