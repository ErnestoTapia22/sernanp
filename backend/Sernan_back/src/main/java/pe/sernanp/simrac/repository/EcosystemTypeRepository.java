package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import pe.gisriv.entity.PaginatorEntity;
import pe.sernanp.simrac.mapper.EcosystemTypeMapper;
import pe.sernanp.simrac.model.EcosystemTypeModel;

@Repository
public class EcosystemTypeRepository extends BaseRepository<EcosystemTypeModel>{

	@Override
	public List<EcosystemTypeModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_tipoecosistema", new EcosystemTypeMapper());
	}
	
	public EcosystemTypeModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_tipoecosistema", id, new EcosystemTypeMapper());
	}
	
	@Override
	public List<EcosystemTypeModel> search(DataSource ds, EcosystemTypeModel item, PaginatorEntity paginator) throws Exception{		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pname", item.getName());
		return super.search2(ds,"simrac.fn_buscar_tipoecosistema",parameters,paginator, new EcosystemTypeMapper());
	}
	
	@Override
	public int insert(DataSource ds, EcosystemTypeModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_tipoecosistema", item);
	}

	@Override
	public int update(DataSource ds, EcosystemTypeModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_tipoecosistema", item);
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "mc.miningconcession_delete", id);
	}
		
}
