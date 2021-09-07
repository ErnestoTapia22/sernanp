package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.AgreementStateModel;
import pe.sernanp.simrac.model.RoleModel;
import pe.sernanp.simrac.model.UserModel;

public class UserMapper extends BaseMapper<UserModel>{
	
	@Override
	public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		UserModel item = super.mapRowBase(rs, new UserModel());
		item.setSystem(ResultSetExtension.getInt2(rs, "system"));
		item.setUserName(ResultSetExtension.getString2(rs, "username"));
		item.setLastName(ResultSetExtension.getString2(rs, "lastname"));
		item.setDocumentNumber(ResultSetExtension.getString2(rs, "documentnumber"));
		item.setRole(new RoleModel());
		super.mapRowWithTable(rs, item.getRole(), "role");
		return item;
	}

}
