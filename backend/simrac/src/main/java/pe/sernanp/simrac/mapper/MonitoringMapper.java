package pe.sernanp.simrac.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.model.MonitoringModel;
import pe.sernanp.simrac.model.WorkPlanModel;

public class MonitoringMapper extends BaseMapper<MonitoringModel>{
	
	@Override
	public MonitoringModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		MonitoringModel item = super.mapRowBase(rs, new MonitoringModel());
		item.setComment(ResultSetExtension.getString2(rs, "comment"));
		item.setAchievement(ResultSetExtension.getString2(rs, "achievement"));
		item.setActive(ResultSetExtension.getBoolean2(rs, "active"));
		item.setRecommendation(ResultSetExtension.getString2(rs, "recommendation"));
		item.setEvaluation(ResultSetExtension.getString2(rs, "evaluation"));
		return item;
	}

}
