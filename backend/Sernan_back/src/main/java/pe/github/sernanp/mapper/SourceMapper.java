package pe.github.sernanp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.github.sernanp.model.SourceModel;

public class SourceMapper extends BaseMapper<SourceModel> {
	@Override
	public SourceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		SourceModel item = super.mapRowBase(rs, new SourceModel());
		return item;
	}
}