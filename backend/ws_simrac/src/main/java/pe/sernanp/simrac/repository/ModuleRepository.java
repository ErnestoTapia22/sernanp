package pe.sernanp.simrac.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.model.ModuleModel;

public interface ModuleRepository extends JpaRepository<ModuleModel, Integer>{
		
	@Query(value="select m.idmodulo, m.nombremodulo, m.nivelmodulo, m.niveldependemodulo, "
			+ "	m.ordenmodulo,m.flagmodulo, m.hrefmodulo from sernanp.modulo as m "
			+ "	inner join sernanp.rolmodulo as rm on rm.idmodulo=m.idmodulo "
			+ "	inner join sernanp.rol as r on r.idrol= rm.idrol "
			+ "	inner join sernanp.usuariorol as ur on ur.idrol = r.idrol "
			+ "	where r.idsistema = :psystem and ur.idusuario = :puserid and m.flagmodulo = 1", nativeQuery=true)
	List<ModuleModel> search(@Param("psystem") int system, @Param("puserid")int userId);
	
	@Query(value="select m.idmodulo, m.nombremodulo, m.nivelmodulo, m.niveldependemodulo, "
			+ " m.ordenmodulo, m.flagmodulo, m.hrefmodulo from sernanp.modulo m where niveldependemodulo in ("
			+ "	select m.idmodulo "
			+ "	from sernanp.modulo as m "
			+ "	inner join sernanp.sistema as s on s.idmodulo = m.idmodulo "
			+ "	where s.idsistema = :psystem)", nativeQuery=true)
	List<ModuleModel> searchBySystem(@Param("psystem") int system);
	
	@Query(value="select m.idmodulo, m.nombremodulo, m.nivelmodulo, m.niveldependemodulo, "
			+ "	m.ordenmodulo, m.flagmodulo, m.hrefmodulo "
			+ "	from sernanp.rolmodulo as rl "
			+ "	inner join sernanp.modulo as m on m.idmodulo = rl.idmodulo "
			+ "	where rl.idrol = :prol", nativeQuery=true)
	List<ModuleModel> searchByRole(@Param("prol") int roleId);
	
}