package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.sernanp.simrac.model.SourceModel;

public class SourceMapper extends BaseMapper<SourceModel> {
	@Override
	public SourceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		SourceModel item = super.mapRowBase(rs, new SourceModel());
		return item;
	}
}