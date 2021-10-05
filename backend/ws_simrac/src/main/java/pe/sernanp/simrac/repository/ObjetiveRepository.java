package pe.sernanp.simrac.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.ObjetiveModel;


public interface ObjetiveRepository extends JpaRepository<ObjetiveModel, Integer>{
		
	@Query(value="select srl_id, var_cod, txt_des, tsp_fec, bol_flg, int_componenteid, int_planmaestroid from simrac.t_objetivo WHERE int_planmaestroid=:id", nativeQuery=true)
    List<ObjetiveModel> searchObjetives(@Param("id") int id);
	
	
}