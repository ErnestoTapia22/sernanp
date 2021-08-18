package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import pe.gisriv.entity.PaginatorEntity;
import pe.sernanp.simrac.mapper.VerificationMeanMapper;
import pe.sernanp.simrac.model.VerificationMeanModel;

public class VerificationMeanRepository extends BaseRepository<VerificationMeanModel> {		
	
	@Override
	public List<VerificationMeanModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_medioverificacion", new VerificationMeanMapper());
	}
	
	public VerificationMeanModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_medioverificacion", id, new VerificationMeanMapper());
	}
	
	@Override
	public List<VerificationMeanModel> search(DataSource ds, VerificationMeanModel item, PaginatorEntity paginator) throws Exception{		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pname", item.getName());
		return super.search2(ds,"simrac.fn_buscar_medioverificacion",parameters,paginator, new VerificationMeanMapper());
	}
	
	@Override
	public int insert(DataSource ds, VerificationMeanModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_medioverificacion", item);
	}

	@Override
	public int update(DataSource ds, VerificationMeanModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_medioverificacion", item);
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_medioverificacion", id);
	}
}