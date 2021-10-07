package pe.sernanp.simrac.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.CommitmentModel;

public interface CommitmentRepository extends JpaRepository<CommitmentModel, Integer>{

	@Query(value="select * from simrac.t_compromiso_externo WHERE INT_ACUERDOID=:id", nativeQuery=true)
	List<CommitmentModel> searchByAgreement(@Param("id") int id);
}