package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.AlliedMapper;
import pe.sernanp.simrac.mapper.ConservationAgreementMapper;
import pe.sernanp.simrac.model.AlliedModel;

@Repository
public class AlliedRepository extends BaseRepository<AlliedModel> {
	
	@Override
	protected void setParameters(Map<String, Object> parameters, AlliedModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());
		
		 parameters.put("pagreementid", item.getConservationAgreement().getId());
		 parameters.put("palliedcategoryid", item.getAlliedCategory().getId());
	}
	
	@Override
	public int update(DataSource ds, AlliedModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_aliado", item);
	}
	
	@Override
	public int insert(DataSource ds, AlliedModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_aliado", item);
	}
	
	public AlliedModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_aliado", id, new AlliedMapper());
	}
		
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_aliado", id);
	}
	
	
	public List<AlliedModel> searchByAgreement(DataSource ds, int id) throws Exception {
		System.out.println(ds);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pagreementid",id);
		return super.search23(ds,"simrac.fn_buscar_aliadoporacuerdo",parameters, new AlliedMapper());
		
	}
		
	
}
