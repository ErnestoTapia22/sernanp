package pe.github.sernanp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.github.sernanp.model.EcosystemTypeModel;

public class EcosystemTypeMapper extends BaseMapper<EcosystemTypeModel> {
	@Override
	public EcosystemTypeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		EcosystemTypeModel item = super.mapRowBase(rs, new EcosystemTypeModel());
		return item;
	}
}
