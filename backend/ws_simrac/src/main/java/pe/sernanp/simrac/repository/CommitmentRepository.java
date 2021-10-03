package pe.sernanp.simrac.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.simrac.model.CommitmentModel;

public interface CommitmentRepository extends JpaRepository<CommitmentModel, Integer>{

}