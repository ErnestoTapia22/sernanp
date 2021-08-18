package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.AgreementStateModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;

public class ConservationAgreementMapper extends BaseMapper<ConservationAgreementModel> {
	@Override
	public ConservationAgreementModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		ConservationAgreementModel item = super.mapRowBase(rs, new ConservationAgreementModel());
		item.setAgreementState(new AgreementStateModel());
		super.mapRowWithTable(rs, item.getAgreementState(), "agreementstate");
		item.setFirm(ResultSetExtension.getDate2(rs, "firm"));
		item.setValidity(ResultSetExtension.getInt2(rs, "vigency"));
		return item;
	}
}