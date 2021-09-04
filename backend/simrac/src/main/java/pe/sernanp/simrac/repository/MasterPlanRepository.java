package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import pe.sernanp.simrac.mapper.ActionLineMapper;
import pe.sernanp.simrac.mapper.MasterPlanMapper;
import pe.sernanp.simrac.mapper.ObjetiveMapper;
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.MasterPlanModel;
import pe.sernanp.simrac.model.ObjetiveModel;

@Repository
public class MasterPlanRepository extends BaseRepository<MasterPlanModel> {

	public MasterPlanModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_planmaestro", id, new MasterPlanMapper());
	}
	
	@Override
	public int insert(DataSource ds, MasterPlanModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_planmaestro", item);
	}

	/*@Override
	public int update(DataSource ds, ActivityModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_acuerdoconservacion", item);
	}*/
	
	@Override
	protected void setParameters(Map<String, Object> parameters, MasterPlanModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("panpid", item.getAnp().getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());		 
		 parameters.put("pstate", item.getState()); 
		 parameters.put("pversion", item.getVersion());
		 parameters.put("pactive", item.getActive());
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_planmaestro", id);
	}
	
	public List<ObjetiveModel> searchObjetives(DataSource ds, int id) throws Exception {		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pagreementid",id);
		return super.search23(ds,"simrac.fn_buscar_objetivosporplanmaestro",parameters, new ObjetiveMapper());		
	}
}
