package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.sernanp.simrac.model.AlliedCommitmentModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;

public class AlliedCommitmentMapper extends BaseMapper <AlliedCommitmentModel> {
	@Override
	public AlliedCommitmentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		AlliedCommitmentModel item = super.mapRowBase(rs, new AlliedCommitmentModel());
		item.setConservationAgreement(new ConservationAgreementModel ());
		super.mapRowWithTable(rs, item.getConservationAgreement(), "agreement");

		return item;
		
		
	}
}
