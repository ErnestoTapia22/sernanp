package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import pe.sernanp.simrac.model.EconomicActivityModel;

public class EconomicActivityMapper extends BaseMapper<EconomicActivityModel> {
	@Override
	public EconomicActivityModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		EconomicActivityModel item = super.mapRowBase(rs, new EconomicActivityModel());
		return item;
	}
}