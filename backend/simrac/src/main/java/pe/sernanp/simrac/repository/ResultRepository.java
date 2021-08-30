package pe.sernanp.simrac.repository;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.model.ResultModel;

@Repository
public class ResultRepository extends BaseRepository<ResultModel>  {

	@Override
	public int insert(DataSource ds, ResultModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_actividad", item);
	}
	
	@Override
	protected void setParameters(Map<String, Object> parameters, ResultModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pplanjobid", item.getPlanJob().getId());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());		 
		 parameters.put("pstate", item.getState()); 
		 parameters.put("pcomment", item.getComment());
	}
}
