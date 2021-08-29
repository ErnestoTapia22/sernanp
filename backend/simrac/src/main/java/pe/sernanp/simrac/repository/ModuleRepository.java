package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.ModuleMapper;
import pe.sernanp.simrac.model.ModuleModel;

@Repository
public class ModuleRepository extends BaseRepository<ModuleModel> {
	
		public List<ModuleModel> buscar(DataSource ds, int id, int id2) throws Exception {
				System.out.println(ds);
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("pidsistema", id);
				parameters.put("pidusuario", id2);
				return super.search23(ds,"simrac.fn_buscar_moduloporusuariosistema",parameters, new ModuleMapper());
				
		}
		
}