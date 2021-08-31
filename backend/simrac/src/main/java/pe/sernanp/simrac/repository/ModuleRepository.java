package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.ModuleMapper;
import pe.sernanp.simrac.model.ModuleModel;

@Repository
public class ModuleRepository extends BaseRepository<ModuleModel> {
	
	public List<ModuleModel> search(DataSource ds, int system, int userId) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		//parameters.put("psystem", system);
		parameters.put("puserid", userId);
		return super.search23(ds,"simrac.fn_buscar_moduloporusuariosistema",parameters, new ModuleMapper());			
	}		
}