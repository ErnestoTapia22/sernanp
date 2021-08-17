package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.sernanp.simrac.model.AgreementStateModel;

public class AgreementStateMapper  extends BaseMapper<AgreementStateModel> {
	@Override
	public AgreementStateModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		AgreementStateModel item = super.mapRowBase(rs, new AgreementStateModel());
		return item;
	}
}