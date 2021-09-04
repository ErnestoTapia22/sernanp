package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.AgreementStateModel;
import pe.sernanp.simrac.model.ComponentModel;
import pe.sernanp.simrac.model.MasterPlanModel;
import pe.sernanp.simrac.model.ObjetiveModel;

public class ActionLineMapper extends BaseMapper<ActionLineModel> {
	@Override
	public ActionLineModel mapRow(ResultSet rs, int rowNum) throws SQLException {		
		ActionLineModel item = super.mapRowBase(rs, new ActionLineModel());
		item.setObjetive(new ObjetiveModel());
		super.mapRowWithTable(rs, item.getObjetive(), "objetive");
		return item;
	}
}