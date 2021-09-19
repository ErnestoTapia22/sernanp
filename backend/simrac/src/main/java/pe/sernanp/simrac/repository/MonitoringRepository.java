package pe.sernanp.simrac.repository;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import pe.sernanp.simrac.mapper.MonitoringMapper;
import pe.sernanp.simrac.mapper.SourceMapper;
import pe.sernanp.simrac.model.MonitoringModel;
import pe.sernanp.simrac.model.SourceModel;

@Repository
public class MonitoringRepository extends BaseRepository<MonitoringModel>  {

	@Override
	public int insert(DataSource ds, MonitoringModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_monitoreo", item);
	}
	
	@Override
	protected void setParameters(Map<String, Object> parameters, MonitoringModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());
		 parameters.put("pcomment", item.getComment());
		 parameters.put("pachievement", item.getAchievement());
		 parameters.put("precommendation", item.getRecommendation());
		 parameters.put("pactive", item.getActive());
		 parameters.put("pevaluation", item.getEvaluation());
	}
	
	public int insertAnswer(DataSource ds, int activityId, int monitoringId, int value, Date registrationDate, boolean state) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("pactivityid", activityId);
		parameters.put("pmonitoringid", monitoringId);
		parameters.put("pvalue", value);		
		parameters.put("pregistrationdate", registrationDate);		
		parameters.put("pstate", state);
		return super.insert(ds, "simrac.fn_insertar_respuesta", parameters);
	}
	
	public MonitoringModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_monitoreo", id, new MonitoringMapper());
	}
}
