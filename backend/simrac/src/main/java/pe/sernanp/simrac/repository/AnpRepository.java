package pe.sernanp.simrac.repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import pe.gisriv.entity.PaginatorEntity;
import pe.sernanp.simrac.mapper.AlliedMapper;
import pe.sernanp.simrac.mapper.AnpMapper;
import pe.sernanp.simrac.model.AlliedModel;
import pe.sernanp.simrac.model.AnpModel;

@Repository
public class AnpRepository extends BaseRepository<AnpModel> {
		
	@Override
	protected void setParameters(Map<String, Object> parameters, AnpModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());
		 parameters.put("pcategory", item.getCategory());
		 parameters.put("pdistrit", item.getDistrict());
		 parameters.put("pcode", item.getCode());		 		
	}
	
	@Override
	public List<AnpModel> search(DataSource ds, AnpModel item, PaginatorEntity paginator) throws Exception{		
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("pcode", item.getCode());
		parameters.put("pname", item.getName());
		return super.search2(ds,"simrac.fn_buscar_anp",parameters,paginator, new AnpMapper());
	}
	
	@Override
	public List<AnpModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_anp", new AnpMapper());
	}
	
	@Override
	public int update(DataSource ds, AnpModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_anp", item);
	}
	
	@Override
	public int insert(DataSource ds, AnpModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_anp", item);
	}
	
	public AnpModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_anp", id, new AnpMapper());
	}
		
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_anp", id);
	}	
}