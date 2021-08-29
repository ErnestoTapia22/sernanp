package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.sernanp.simrac.model.CommitmentEcaModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;

public class CommitmentEcaMapper extends BaseMapper<CommitmentEcaModel> {
	@Override
	public CommitmentEcaModel mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		CommitmentEcaModel item = super.mapRowBase(rs, new CommitmentEcaModel());
		item.setConservationAgreement(new ConservationAgreementModel ());
		super.mapRowWithTable(rs, item.getConservationAgreement(), "agreement");

		return item;
	}

}
