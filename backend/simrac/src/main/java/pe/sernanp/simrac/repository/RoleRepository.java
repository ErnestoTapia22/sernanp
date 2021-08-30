package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.RoleMapper;
import pe.sernanp.simrac.model.RoleModel;

@Repository
public class RoleRepository extends BaseRepository<RoleModel>{

	
	public List<RoleModel> search(DataSource ds, int id) throws Exception {
		System.out.println(ds);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("psystem",id);
		return super.search23(ds,"simrac.fn_buscar_rol",parameters, new RoleMapper());
		
	}
	
}
