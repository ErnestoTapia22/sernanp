package pe.github.sernanp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.github.sernanp.entity.PaginatorEntity;
import pe.github.sernanp.mapper.ArticulateMapper;
import pe.github.sernanp.model.ArticulateModel;


@Repository
public class ArticulateRepository  extends BaseRepository<ArticulateModel> {
	
	@Override
	public List<ArticulateModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_articulado", new ArticulateMapper());
	}
	
	public ArticulateModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_articulado", id, new ArticulateMapper());
	}
	
	@Override
	public List<ArticulateModel> search(DataSource ds, ArticulateModel item, PaginatorEntity paginator) throws Exception{		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pname", item.getName());
		return super.search2(ds,"simrac.fn_buscar_articulado",parameters,paginator, new ArticulateMapper());
	}
	
	@Override
	public int insert(DataSource ds, ArticulateModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_articulado", item);
	}

	@Override
	public int update(DataSource ds, ArticulateModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_articulado", item);
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_articulado", id);
	}

}
