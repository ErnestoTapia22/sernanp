package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.WorkPlanModel;

public class WorkPlanMapper extends BaseMapper<WorkPlanModel>{
	
	@Override
	public WorkPlanModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		WorkPlanModel item = super.mapRowBase(rs, new WorkPlanModel());

		item.setYear(ResultSetExtension.getInt2(rs, "year"));
		item.setVersion(ResultSetExtension.getInt2(rs, "version"));
		item.setActive(ResultSetExtension.getBoolean2(rs, "active"));
		
		return item;
	}

}
