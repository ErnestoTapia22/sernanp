package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.UserMapper;
import pe.sernanp.simrac.model.UserModel;


@Repository
public class UserRepository extends BaseRepository<UserModel>{
	
	@Override
	protected void setParameters(Map<String, Object> parameters, UserModel item) throws Exception {
		
		parameters.put("psystem", item.getSystem());
		
	}	
		
	
	public UserModel validate(DataSource ds, String id) throws Exception {
			System.out.println(ds);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("puser", id);
			return super.detail2(ds,"simrac.fn_validar_usuario", parameters, new UserMapper());
			
	}
	
}