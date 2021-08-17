package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.sernanp.simrac.model.VerificationMeanModel;

public class VerificationMeanMapper extends BaseMapper<VerificationMeanModel> {
	@Override
	public VerificationMeanModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		VerificationMeanModel item = super.mapRowBase(rs, new VerificationMeanModel());
		return item;
	}
}
