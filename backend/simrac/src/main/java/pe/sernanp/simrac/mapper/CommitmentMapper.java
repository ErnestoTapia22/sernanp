package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.AlliedCategoryModel;
import pe.sernanp.simrac.model.AlliedModel;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.model.ObjetiveModel;

public class CommitmentMapper extends BaseMapper <CommitmentModel> {
	
	@Override
	public CommitmentModel mapRow (ResultSet rs, int rowNum) throws SQLException {
		
		CommitmentModel item = super.mapRowBase(rs, new CommitmentModel());
		item.setConservationAgreement(new ConservationAgreementModel ());
		super.mapRowWithTable (rs, item.getConservationAgreement(), "agreement");
		
		item.setAllied(new AlliedModel());
		super.mapRowWithTable (rs, item.getAllied(), "allied");
		
		item.setActionLine(new ActionLineModel());
		super.mapRowWithTable (rs, item.getActionLine(), "actionline");
		
		
		item.setIndicator(ResultSetExtension.getString2(rs, "indicator"));
		item.setActive(ResultSetExtension.getBoolean2(rs, "active"));
	
		return item;
	}

}
