package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.RoleMapper;
import pe.sernanp.simrac.model.AlliedCommitmentModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.model.RoleModel;
import pe.sernanp.simrac.model.VerificationMeanModel;

@Repository
public class RoleRepository extends BaseRepository<RoleModel>{

	
	@Override
	protected void setParameters(Map<String, Object> parameters, RoleModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pflag", item.getFlag());
		 parameters.put("psystem", item.getSystem());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
	}
	
	
	public List<RoleModel> search(DataSource ds, int id) throws Exception {
		System.out.println(ds);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("psystem",id);
		return super.search23(ds,"simrac.fn_buscar_rol",parameters, new RoleMapper());
				
	}
	
	@Override
	public int insert(DataSource ds, RoleModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_rol", item);
	}
	
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_rol", id);
	}
	
}
