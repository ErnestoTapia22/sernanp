package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import pe.gisriv.entity.PaginatorEntity;
import pe.sernanp.simrac.mapper.AgreementStateMapper;
import pe.sernanp.simrac.model.AgreementStateModel;

@Repository
public class AgreementStateRepository extends BaseRepository<AgreementStateModel>{
	
	@Override
	public List<AgreementStateModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_estadoacuerdo", new AgreementStateMapper());
	}
	
	public AgreementStateModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_estadoacuerdo", id, new AgreementStateMapper());
	}
	
	@Override
	public List<AgreementStateModel> search(DataSource ds, AgreementStateModel item, PaginatorEntity paginator) throws Exception{		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pname", item.getName());
		return super.search2(ds,"simrac.fn_buscar_estadoacuerdo",parameters,paginator, new AgreementStateMapper());
		//return super.search(ds,"simrac.buscar_actividadeconomica",item,paginator, new EconomicActivityMapper());
	}
	
	@Override
	public int insert(DataSource ds, AgreementStateModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_estadoacuerdo", item);
	}

	@Override
	public int update(DataSource ds, AgreementStateModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_estadoacuerdo", item);
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_estadoacuerdo", id);
	}
}