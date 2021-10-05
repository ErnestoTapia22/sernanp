package pe.sernanp.simrac.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.model.ModuleModel;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, Integer>{
		
	@Query(value="select m.idmodulo as id, m.nombremodulo as name, m.nivelmodulo as level, m.niveldependemodulo as moduleid, "
			+ "	m.ordenmodulo as order,m.flagmodulo as flag, m.hrefmodulo as path from sernanp.modulo as m "
			+ "	inner join sernanp.rolmodulo as rm on rm.idmodulo=m.idmodulo "
			+ "	inner join sernanp.rol as r on r.idrol= rm.idrol "
			+ "	inner join sernanp.usuariorol as ur on ur.idrol = r.idrol "
			+ "	where r.idsistema = :psystem and ur.idusuario = :puserid and m.flagmodulo = :puserid", nativeQuery=true)
	List<ModuleModel> search(@Param("psystem") int system, @Param("puserid")int userId);
}