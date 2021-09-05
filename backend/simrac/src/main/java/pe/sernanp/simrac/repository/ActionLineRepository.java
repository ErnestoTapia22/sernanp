package pe.sernanp.simrac.repository;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import pe.sernanp.simrac.mapper.ActionLineMapper;
import pe.sernanp.simrac.model.ActionLineModel;

@Repository
public class ActionLineRepository extends BaseRepository<ActionLineModel> {

		
	@Override
	public int insert(DataSource ds, ActionLineModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_lineaaccion", item);
	}
	
	@Override
	protected void setParameters(Map<String, Object> parameters, ActionLineModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pobjetiveid", item.getObjetive().getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());		 
		 parameters.put("pstate", item.getState());
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_lineaaccion", id);
	}
}
