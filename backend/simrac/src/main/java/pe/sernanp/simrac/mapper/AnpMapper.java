package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.AnpModel;

public class AnpMapper extends BaseMapper<AnpModel> {
	@Override
	public AnpModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		AnpModel item = super.mapRowBase(rs, new AnpModel());
		item.setCategory(ResultSetExtension.getString2(rs, "category"));
		item.setDistrict(ResultSetExtension.getString2(rs, "district"));
		item.setWithMasterPlan(ResultSetExtension.getInt2(rs, "withmasterplan"));
		return item;
	}
}