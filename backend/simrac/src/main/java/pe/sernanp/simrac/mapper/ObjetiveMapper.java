package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.AgreementStateModel;
import pe.sernanp.simrac.model.ComponentModel;
import pe.sernanp.simrac.model.MasterPlanModel;
import pe.sernanp.simrac.model.ObjetiveModel;

public class ObjetiveMapper extends BaseMapper<ObjetiveModel> {
	@Override
	public ObjetiveModel mapRow(ResultSet rs, int rowNum) throws SQLException {		
		ObjetiveModel item = super.mapRowBase(rs, new ObjetiveModel());
		item.setComponent(new ComponentModel());
		super.mapRowWithTable(rs, item.getComponent(), "component");
		item.setMasterPlan(new MasterPlanModel());
		super.mapRowWithTable(rs, item.getMasterPlan(), "masterplan");
		return item;
	}
}