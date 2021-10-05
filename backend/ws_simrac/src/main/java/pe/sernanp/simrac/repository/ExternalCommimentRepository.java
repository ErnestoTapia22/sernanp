package pe.sernanp.simrac.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.ExternalCommimentModel;

public interface ExternalCommimentRepository extends JpaRepository<ExternalCommimentModel, Integer>{
	
	@Query(value="select SRL_ID, txt_lineaaccion, int_acuerdoid,  txt_des, txt_objetivo, tsp_fec, BOL_FLG from simrac.t_compromiso_externo WHERE INT_ACUERDOID=:id", nativeQuery=true)
	List<ExternalCommimentModel> searchByAgreement(@Param("id") int id);

}