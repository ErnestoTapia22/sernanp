package pe.github.sernanp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.github.sernanp.entity.PaginatorEntity;
import pe.github.sernanp.mapper.SourceMapper;
import pe.github.sernanp.model.SourceModel;

@Repository
public class SourceRepository extends BaseRepository<SourceModel> {
	@Override
	public List<SourceModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_fuente", new SourceMapper());
	}
	
	public SourceModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_fuente", id, new SourceMapper());
	}
	
	@Override
	public List<SourceModel> search(DataSource ds, SourceModel item, PaginatorEntity paginator) throws Exception{		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pname", item.getName());
		return super.search2(ds,"simrac.fn_buscar_fuente",parameters,paginator, new SourceMapper());
	}
	
	@Override
	public int insert(DataSource ds, SourceModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_fuente", item);
	}

	@Override
	public int update(DataSource ds, SourceModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_fuente", item);
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_fuente", id);
	}
}