package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.RoleModel;

public class RoleMapper extends BaseMapper<RoleModel> {
	
	@Override
	public RoleModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		RoleModel item = super.mapRowBase(rs, new RoleModel());
				
		item.setSystem(ResultSetExtension.getInt2(rs, "system"));
		
		return item;
	}
}