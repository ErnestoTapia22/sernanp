package pe.sernanp.simrac.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.RoleModel;

public interface RoleRepository extends JpaRepository<RoleModel, Integer>{
		
	@Query(value="select idrol, desrol, flagrol, idsistema, tsp_fecha_reg from sernanp.rol where idsistema = :psystem", nativeQuery=true)
	List<RoleModel> search(@Param("psystem") int system);
	
	@Modifying
	@Query(value="delete from sernanp.rolmodulo where idrol = :prol", nativeQuery=true)
	int deleteModule(@Param("prol") int prol);
	
	@Modifying
	@Query(value="INSERT INTO sernanp.rolmodulo(idrol, idmodulo, pesorol, flagmodulodefault) VALUES (:pid, :pmoduleid, 1, 1)", nativeQuery=true)
	int insert2(@Param("pid") int pid, @Param("pmoduleid") int pmoduleid);
}