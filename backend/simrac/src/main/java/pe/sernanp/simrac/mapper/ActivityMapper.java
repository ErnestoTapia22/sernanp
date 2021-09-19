package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.MonitoringModel;
import pe.sernanp.simrac.model.ObjetiveModel;
import pe.sernanp.simrac.model.WorkPlanModel;

public class ActivityMapper extends BaseMapper<ActivityModel> {
	@Override
	public ActivityModel mapRow(ResultSet rs, int rowNum) throws SQLException {		
		ActivityModel item = super.mapRowBase(rs, new ActivityModel());
		item.setIndicator(ResultSetExtension.getString2(rs, "indicator"));
		item.setGoal(ResultSetExtension.getInt2(rs, "goal"));
		item.setValue(ResultSetExtension.getInt2(rs, "value"));
		item.setSemester(ResultSetExtension.getString2(rs, "semester"));
		item.setCommitment(new CommitmentModel());
		super.mapRowWithTable(rs, item.getCommitment(), "commitment");
		item.getCommitment().setActionLine(new ActionLineModel());
		super.mapRowWithTable(rs, item.getCommitment().getActionLine(), "actionline");
		item.getCommitment().getActionLine().setObjetive(new ObjetiveModel());
		super.mapRowWithTable(rs, item.getCommitment().getActionLine().getObjetive(), "objetive");	
		item.setWorkPlan(new WorkPlanModel());
		super.mapRowWithTable(rs, item.getWorkPlan(), "workplan");	
		item.setMonitoring(new MonitoringModel());
		super.mapRowWithTable(rs, item.getMonitoring(), "monitoring");
		return item;
	}
}