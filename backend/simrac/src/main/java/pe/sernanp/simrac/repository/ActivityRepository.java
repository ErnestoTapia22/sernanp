package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.ActivityMapper;
import pe.sernanp.simrac.mapper.ModuleMapper;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.ModuleModel;
import pe.sernanp.simrac.model.WorkPlanModel;

@Repository
public class ActivityRepository extends BaseRepository<ActivityModel> {

	public ActivityModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_actividad", id, new ActivityMapper());
	}
	
	@Override
	public int insert(DataSource ds, ActivityModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_actividad", item);
	}
	
	@Override
	protected void setParameters(Map<String, Object> parameters, ActivityModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pworkplanid", item.getWorkPlan().getId2());
		 parameters.put("pcommitmentid", item.getCommitment().getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());		 
		 parameters.put("pstate", item.getState());
		 parameters.put("pgoal", item.getGoal());
		 parameters.put("pindicator", item.getIndicator());
		 parameters.put("pactive", item.getActive());
		 parameters.put("psemester", item.getSemester());
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_acuerdoconservacion", id);
	}
	
	public List<ActivityModel> searchByAgreement(DataSource ds, int id) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("pagreementid", id);
		return super.search23(ds,"simrac.fn_buscar_actividadporacuerdo",parameters, new ActivityMapper());			
	}
	
	public List<ActivityModel> searchByCommitment(DataSource ds, int id) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("pcommitmentid", id);
		return super.search23(ds,"simrac.fn_buscar_actividadporcompromiso",parameters, new ActivityMapper());			
	}
	
	public List<ActivityModel> searchByMonitoringAndAgreement(DataSource ds, int id) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("pagreementid", id);
		return super.search23(ds,"simrac.fn_buscar_actividadpormonitoreoacuerdo",parameters, new ActivityMapper());			
	}
}
