package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;

public class CommitmentMapper extends BaseMapper <CommitmentModel> {
	
	@Override
	public CommitmentModel mapRow (ResultSet rs, int rowNum) throws SQLException {
		
		CommitmentModel item = super.mapRowBase(rs, new CommitmentModel());
		item.setConservationAgreement(new ConservationAgreementModel ());
		super.mapRowWithTable (rs, item.getConservationAgreement(), "agreement");

		return item;
	}

}
