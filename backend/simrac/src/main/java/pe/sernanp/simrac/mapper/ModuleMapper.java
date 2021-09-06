package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.ModuleModel;
import pe.sernanp.simrac.model.UserModel;

public class ModuleMapper extends BaseMapper<ModuleModel>{
	
	@Override
	public ModuleModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ModuleModel item = super.mapRowBase(rs, new ModuleModel());
		
		item.setLevel(ResultSetExtension.getInt2(rs, "level"));
		item.setModuleid(ResultSetExtension.getInt2(rs, "moduleid"));
		item.setOrder(ResultSetExtension.getInt2(rs, "order"));
		item.setFlag(ResultSetExtension.getInt2(rs, "flag"));
		item.setPath(ResultSetExtension.getString2(rs, "path"));
					
		return item;
	}
}