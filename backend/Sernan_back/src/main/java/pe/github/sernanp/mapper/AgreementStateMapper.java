package pe.github.sernanp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.github.sernanp.model.AgreementStateModel;

public class AgreementStateMapper  extends BaseMapper<AgreementStateModel> {
	@Override
	public AgreementStateModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		AgreementStateModel item = super.mapRowBase(rs, new AgreementStateModel());
		return item;
	}
}