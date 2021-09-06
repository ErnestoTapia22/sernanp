package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.UserModel;

public class UserMapper extends BaseMapper<UserModel>{
	
	@Override
	public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		UserModel item = super.mapRowBase(rs, new UserModel());
		item.setSystem(ResultSetExtension.getInt2(rs, "system"));
		
		
		
		return item;
	}

}
