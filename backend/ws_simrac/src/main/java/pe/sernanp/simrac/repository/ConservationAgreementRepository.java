package pe.sernanp.simrac.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;

public interface ConservationAgreementRepository extends JpaRepository<ConservationAgreementModel, Integer> {

	@Query(value="select * from simrac.t_acuerdo_conservacion c where 1=1",
			countQuery = "SELECT count(*) FROM simrac.t_acuerdo_conservacion where 1=1",
			nativeQuery=true)
	Page<ConservationAgreementModel> findAll(String d, String b, Pageable page);
	
}