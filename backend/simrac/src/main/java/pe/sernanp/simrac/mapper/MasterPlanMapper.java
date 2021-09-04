package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.MasterPlanModel;

public class MasterPlanMapper extends BaseMapper<MasterPlanModel> {
	@Override
	public MasterPlanModel mapRow(ResultSet rs, int rowNum) throws SQLException {		
		MasterPlanModel item = super.mapRowBase(rs, new MasterPlanModel());
		item.setVersion(ResultSetExtension.getInt2(rs, "vigency"));
		item.setActive(ResultSetExtension.getBoolean2(rs, "partmen"));
		return item;
	}
}