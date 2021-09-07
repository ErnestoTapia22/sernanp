package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.ModuleMapper;
import pe.sernanp.simrac.model.ModuleModel;
import pe.sernanp.simrac.model.UserModel;

@Repository
public class ModuleRepository extends BaseRepository<ModuleModel> {
	
	public List<ModuleModel> search(DataSource ds, int system, int userId) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("psystem", system);
		parameters.put("puserid", userId);
		return super.search23(ds,"simrac.fn_buscar_moduloporusuariosistema",parameters, new ModuleMapper());			
	}	
	
	public List<ModuleModel> searchBySystem(DataSource ds, int system) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("psystem", system);
		return super.search23(ds,"simrac.fn_buscar_moduloporsistema",parameters, new ModuleMapper());			
	}	
	
	@Override
	public int insert(DataSource ds, ModuleModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_rolmodulo", item);
	}
	
	@Override
	protected void setParameters(Map<String, Object> parameters, ModuleModel item) throws Exception {
		 parameters.put("pmoduleid", item.getId2());
		 parameters.put("pid", item.getRole().getId2());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
	}
}