package pe.sernanp.simrac.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.simrac.model.MonitoringModel;

public interface MonitoringRepository extends JpaRepository<MonitoringModel, Integer> {

}