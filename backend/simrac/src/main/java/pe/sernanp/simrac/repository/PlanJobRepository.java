package pe.sernanp.simrac.repository;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.model.PlanJobModel;

@Repository
public class PlanJobRepository extends BaseRepository<PlanJobModel> {

	@Override
	public int insert(DataSource ds, PlanJobModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_actividad", item);
	}
	
	@Override
	protected void setParameters(Map<String, Object> parameters, PlanJobModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());		 
		 parameters.put("pstate", item.getState()); 
		 parameters.put("pyear", item.getYear());
		 parameters.put("pversion", item.getVersion());
		 parameters.put("pactive", item.getActive());
	}
}
