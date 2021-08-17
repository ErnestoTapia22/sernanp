package pe.github.sernanp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.github.sernanp.entity.PaginatorEntity;
import pe.github.sernanp.mapper.AlliedCategoryMapper;
import pe.github.sernanp.model.AlliedCategoryModel;

@Repository
public class AlliedCategoryRepository extends BaseRepository<AlliedCategoryModel> {
	

	@Override
	public List<AlliedCategoryModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_categoriaaliado", new AlliedCategoryMapper());
	}
	
	public AlliedCategoryModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_categoriaaliado", id, new AlliedCategoryMapper());
	}
	
	@Override
	public List<AlliedCategoryModel> search(DataSource ds, AlliedCategoryModel item, PaginatorEntity paginator) throws Exception{		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pname", item.getName());
		return super.search2(ds,"simrac.fn_buscar_categoriaaliado",parameters,paginator, new AlliedCategoryMapper());
	}
	
	@Override
	public int insert(DataSource ds, AlliedCategoryModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_categoriaaliado", item);
	}

	@Override
	public int update(DataSource ds, AlliedCategoryModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_categoriaaliado", item);
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_categoriaaliado", id);
	}
}