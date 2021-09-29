package pe.sernanp.simrac.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.simrac.model.SourceModel;

public interface SourceRepository extends JpaRepository<SourceModel, Integer>{

}