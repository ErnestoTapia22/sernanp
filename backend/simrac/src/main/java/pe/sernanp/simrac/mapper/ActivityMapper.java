package pe.sernanp.simrac.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.CommitmentModel;

public class ActivityMapper extends BaseMapper<ActivityModel> {
	@Override
	public ActivityModel mapRow(ResultSet rs, int rowNum) throws SQLException {		
		ActivityModel item = super.mapRowBase(rs, new ActivityModel());
		item.setIndicator(ResultSetExtension.getString2(rs, "indicator"));
		item.setGoal(ResultSetExtension.getInt2(rs, "goal"));
		item.setCommitment(new CommitmentModel());
		super.mapRowWithTable(rs, item.getCommitment(), "commitment");		
		return item;
	}
}