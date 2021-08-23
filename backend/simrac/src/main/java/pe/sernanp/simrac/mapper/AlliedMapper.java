package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import pe.sernanp.simrac.model.AlliedCategoryModel;
import pe.sernanp.simrac.model.AlliedModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;

public class AlliedMapper extends BaseMapper<AlliedModel>{
	@Override
	public AlliedModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		AlliedModel item = super.mapRowBase(rs, new AlliedModel());

		item.setAlliedCategory(new AlliedCategoryModel());
		super.mapRowWithTable(rs, item.getAlliedCategory(), "alliedcategory");
		item.setConservationAgreement(new ConservationAgreementModel());
		super.mapRowWithTable(rs, item.getConservationAgreement(), "agreement");
		
		return item;
		
	}
}

	
