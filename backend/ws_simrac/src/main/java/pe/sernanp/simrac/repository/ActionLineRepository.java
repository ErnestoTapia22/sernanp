package pe.sernanp.simrac.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.ActionLineModel;

public interface ActionLineRepository extends JpaRepository<ActionLineModel, Integer>{
	
	@Query(value="select srl_id, var_nom, txt_des, tsp_fec, bol_flg, int_objetivoid from simrac.t_linea_accion WHERE srl_id=:id", nativeQuery=true)
    List<ActionLineModel> searchActionLines(@Param("id") int id);
	
}