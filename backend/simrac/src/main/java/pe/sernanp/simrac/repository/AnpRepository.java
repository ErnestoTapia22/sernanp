package pe.sernanp.simrac.repository;

import java.util.LinkedHashMap;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import pe.gisriv.entity.PaginatorEntity;
import pe.sernanp.simrac.mapper.AnpMapper;
import pe.sernanp.simrac.model.AnpModel;

@Repository
public class AnpRepository extends BaseRepository<AnpModel> {
		
	@Override
	public List<AnpModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_anp", new AnpMapper());
	}
	
	@Override
	public List<AnpModel> search(DataSource ds, AnpModel item, PaginatorEntity paginator) throws Exception{		
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("pcode", item.getCode());
		parameters.put("pname", item.getName());
		return super.search2(ds,"simrac.fn_buscar_anp",parameters,paginator, new AnpMapper());
	}
}
