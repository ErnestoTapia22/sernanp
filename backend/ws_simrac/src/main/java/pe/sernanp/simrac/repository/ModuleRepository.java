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
}