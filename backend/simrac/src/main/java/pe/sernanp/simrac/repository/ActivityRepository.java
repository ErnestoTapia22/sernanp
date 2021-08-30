package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.ActivityMapper;
import pe.sernanp.simrac.model.ActivityModel;

@Repository
public class ActivityRepository extends BaseRepository<ActivityModel> {

	public ActivityModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_actividad", id, new ActivityMapper());
	}
	
	@Override
	public int insert(DataSource ds, ActivityModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_actividad", item);
	}

	/*@Override
	public int update(DataSource ds, ActivityModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_acuerdoconservacion", item);
	}*/
	
	@Override
	protected void setParameters(Map<String, Object> parameters, ActivityModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pplanjobid", item.getPlanJob().getId());
		 parameters.put("pcommimentid", item.getCommitment().getId());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());		 
		 parameters.put("pstate", item.getState()); 
		 parameters.put("pgoal", item.getGoal());
		 parameters.put("pvalue", item.getValue());
		 parameters.put("pindicator", item.getIndicator());
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_acuerdoconservacion", id);
	}
}
