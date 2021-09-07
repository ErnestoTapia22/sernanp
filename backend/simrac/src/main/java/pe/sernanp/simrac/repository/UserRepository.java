package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.gisriv.entity.PaginatorEntity;
import pe.sernanp.simrac.mapper.ModuleMapper;
import pe.sernanp.simrac.mapper.UserMapper;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.model.ModuleModel;
import pe.sernanp.simrac.model.UserModel;


@Repository
public class UserRepository extends BaseRepository<UserModel>{
				
	public UserModel validate(DataSource ds, String id) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("puser", id);
		return super.detail2(ds,"simrac.fn_validar_usuario", parameters, new UserMapper());			
	}	
	
	@Override
	public List<UserModel> search(DataSource ds, UserModel item, PaginatorEntity paginator) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("psystem", item.getSystem());
		parameters.put("pusername", item.getUserName());
		parameters.put("pname", item.getName());
		parameters.put("plastname", item.getLastName());
		parameters.put("proleid", item.getRole().getId2());
		return super.search2(ds,"simrac.fn_buscar_usuario",parameters,paginator, new UserMapper());
	}
	
	public List<UserModel> searchWithoutLogin(DataSource ds, String dni, int system) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("pdni", dni);
		parameters.put("psystem", system);
		return super.search23(ds,"simrac.fn_buscar_usuariosinlogin",parameters, new UserMapper());			
	}	
	
	@Override
	public int insert(DataSource ds, UserModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_usuariorol", item);
	}
	
	@Override
	protected void setParameters(Map<String, Object> parameters, UserModel item) throws Exception {		 
		 parameters.put("proleid", item.getRole().getId2());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pid", item.getId2());
	}
	
}