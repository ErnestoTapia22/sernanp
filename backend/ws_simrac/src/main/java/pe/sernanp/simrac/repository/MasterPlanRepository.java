package pe.sernanp.simrac.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.simrac.model.MasterPlanModel;

public interface MasterPlanRepository extends JpaRepository<MasterPlanModel, Integer>{

}