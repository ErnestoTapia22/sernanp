package pe.sernanp.simrac.repository;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.model.MonitoringModel;

@Repository
public class MonitoringRepository extends BaseRepository<MonitoringModel>  {

	@Override
	public int insert(DataSource ds, MonitoringModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_monitoreo", item);
	}
	
	@Override
	protected void setParameters(Map<String, Object> parameters, MonitoringModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());		 
		 parameters.put("pstate", item.getState()); 
		 parameters.put("pcomment", item.getComment());
	}
}
