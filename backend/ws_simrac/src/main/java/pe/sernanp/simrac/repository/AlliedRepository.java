package pe.sernanp.simrac.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.AlliedModel;


public interface AlliedRepository  extends JpaRepository<AlliedModel, Integer>{

	@Query(value="select SRL_ID, VAR_NOM, TXT_DES, TSP_FEC, INT_CATEGORIAALIADOID, INT_ACUERDOID, BOL_FLG from simrac.t_aliado WHERE INT_ACUERDOID=:id", nativeQuery=true)
	List<AlliedModel> searchByAgreement(@Param("id") int id);
	
}