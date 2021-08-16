package pe.github.sernanp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;
import pe.github.sernanp.entity.PaginatorEntity;
import pe.github.sernanp.mapper.EconomicActivityMapper;
import pe.github.sernanp.model.EconomicActivityModel;

@Repository
public class EconomicActivityRepository extends BaseRepository<EconomicActivityModel> {		
	
	@Override
	public List<EconomicActivityModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_actividadeconomica", new EconomicActivityMapper());
	}
	
	public EconomicActivityModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_actividadeconomica", id, new EconomicActivityMapper());
	}
	
	@Override
	public List<EconomicActivityModel> search(DataSource ds, EconomicActivityModel item, PaginatorEntity paginator) throws Exception{		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pname", item.getName());
		return super.search2(ds,"simrac.buscar_actividadeconomica",parameters,paginator, new EconomicActivityMapper());
		//return super.search(ds,"simrac.buscar_actividadeconomica",item,paginator, new EconomicActivityMapper());
	}
	
	@Override
	public int insert(DataSource ds, EconomicActivityModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_actividadeconomica", item);
	}

	@Override
	public int update(DataSource ds, EconomicActivityModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_actividadeconomica", item);
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_actividadeconomica", id);
	}
}
