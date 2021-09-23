package pe.sernanp.simrac.repository;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.ComponentMapper;
import pe.sernanp.simrac.model.ComponentModel;

@Repository
public class ComponentRepository extends BaseRepository<ComponentModel> {		
	
	@Override
	protected void setParameters(Map<String, Object> parameters, ComponentModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());	
		
	}
	
	@Override
	public List<ComponentModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_componente", new ComponentMapper());
	}
	
	@Override
	public int update(DataSource ds, ComponentModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_componente", item);
	}
	
	@Override
	public int insert(DataSource ds, ComponentModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_componente", item);
	}
	
	public ComponentModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_componente", id, new ComponentMapper());
	}
		
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_componente", id);
	}		
}