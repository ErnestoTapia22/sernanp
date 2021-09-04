package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import pe.sernanp.simrac.mapper.ActionLineMapper;
import pe.sernanp.simrac.mapper.CommitmentMapper;
import pe.sernanp.simrac.mapper.ObjetiveMapper;
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.ObjetiveModel;

@Repository
public class ObjetiveRepository extends BaseRepository<ObjetiveModel> {

	public ObjetiveModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_objetivo", id, new ObjetiveMapper());
	}
	
	@Override
	public int insert(DataSource ds, ObjetiveModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_objetivo", item);
	}

	/*@Override
	public int update(DataSource ds, ActivityModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_acuerdoconservacion", item);
	}*/
	
	@Override
	protected void setParameters(Map<String, Object> parameters, ObjetiveModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pmasterplanid", item.getMasterPlan().getId2());
		 parameters.put("pcomponentid", item.getComponent().getId2());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());		 
		 parameters.put("pstate", item.getState());
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_objetivo", id);
	}
	
	public List<ActionLineModel> searchActionLines(DataSource ds, int id) throws Exception {		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pobjetiveid",id);
		return super.search23(ds,"simrac.fn_buscar_lineasaccionporobjetivo",parameters, new ActionLineMapper());		
	}
}
