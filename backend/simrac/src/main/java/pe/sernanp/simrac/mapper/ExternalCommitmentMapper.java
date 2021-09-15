package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.sernanp.simrac.model.ExternalCommitmentModel;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.ConservationAgreementModel;

public class ExternalCommitmentMapper extends BaseMapper<ExternalCommitmentModel> {
	@Override
	public ExternalCommitmentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		ExternalCommitmentModel item = super.mapRowBase(rs, new ExternalCommitmentModel());
		item.setConservationAgreement(new ConservationAgreementModel ());
		super.mapRowWithTable(rs, item.getConservationAgreement(), "agreement");

		item.setObjetive(ResultSetExtension.getString2(rs, "objetive"));
		item.setActionLine(ResultSetExtension.getString2(rs, "actionline"));
		
		return item;
	}

}
