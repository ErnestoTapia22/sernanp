package pe.sernanp.simrac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.MasterPlanModel;

public interface MasterPlanRepository extends JpaRepository<MasterPlanModel, Integer>{

	@Modifying
	@Query(value="update simrac.t_plan_maestro set bol_activo = false where int_anpid = :panpid", nativeQuery=true)
    int updatePlanActive(@Param("panpid") int id);
	
	@Query(value="select SRL_ID, var_nom, bol_activo, int_anpid, txt_des, tsp_fec, BOL_FLG, int_ver from simrac.t_plan_maestro WHERE int_anpid=:id and bol_activo=true", nativeQuery=true)
	MasterPlanModel searchByANP(@Param("id") int id);
	
}