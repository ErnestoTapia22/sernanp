package pe.github.sernanp.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import pe.github.sernanp.model.ConservationAgreementModel;

public class ConservationAgreementMapper extends BaseMapper<ConservationAgreementModel> {
	@Override
	public ConservationAgreementModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		ConservationAgreementModel item = super.mapRowBase(rs, new ConservationAgreementModel());
		return item;
	}
}