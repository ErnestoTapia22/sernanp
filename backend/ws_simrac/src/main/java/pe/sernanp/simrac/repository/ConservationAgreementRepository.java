package pe.sernanp.simrac.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.simrac.model.ConservationAgreementModel;

public interface ConservationAgreementRepository extends JpaRepository<ConservationAgreementModel, Integer> {

}