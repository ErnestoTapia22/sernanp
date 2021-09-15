package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.model.DistrictModel;

public class DistrictMapper extends BaseMapper<DistrictModel> {
	@Override
	public DistrictModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		DistrictModel item = super.mapRowBase(rs, new DistrictModel());
		item.setDepartment(ResultSetExtension.getString2(rs, "department"));
		item.setProvince(ResultSetExtension.getString2(rs, "province"));
		return item;
	}
}