package pe.sernanp.simrac.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.ComponentMapper;
import pe.sernanp.simrac.model.ComponentModel;

@Repository
public class ComponentRepository extends BaseRepository<ComponentModel> {		
	
	@Override
	public List<ComponentModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_componente", new ComponentMapper());
	}
	
}