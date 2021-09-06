package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import pe.sernanp.simrac.model.ComponentModel;
import pe.sernanp.simrac.model.EconomicActivityModel;

public class ComponentMapper extends BaseMapper<ComponentModel> {
	@Override
	public ComponentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		ComponentModel item = super.mapRowBase(rs, new ComponentModel());
		return item;
	}
}