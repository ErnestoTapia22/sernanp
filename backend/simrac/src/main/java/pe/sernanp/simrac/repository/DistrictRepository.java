package pe.sernanp.simrac.repository;

import java.util.LinkedHashMap;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.DistrictMapper;
import pe.sernanp.simrac.model.DistrictModel;

@Repository
public class DistrictRepository extends BaseRepository<DistrictModel> {
		
	@Override
	public List<DistrictModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_departamento", new DistrictMapper());
	}	
	
	public List<DistrictModel> searchByDepartment(DataSource ds, String code) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("pdepartment", code);
		return super.search23(ds,"simrac.fn_buscar_provincia",parameters, new DistrictMapper());			
	}
	
	public List<DistrictModel> searchByProvince(DataSource ds, String code) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("pcode", code);
		return super.search23(ds,"simrac.fn_buscar_distrito",parameters, new DistrictMapper());			
	}
}
