package pe.sernanp.simrac.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.MonitoringModel;
import pe.sernanp.simrac.repository.ActivityRepository;
import pe.sernanp.simrac.repository.MonitoringRepository;

@Service
public class MonitoringService {
	
	@Autowired
	private MonitoringRepository _repository;
	
	@Autowired
	private ActivityRepository _activityRepository;
	
	public ResponseEntity save (MonitoringModel item) throws Exception{
		try {
			Integer id = item.getId();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			item.setRegistrationDate(item.getRegistrationDate());
			if (id == 0) {
				MonitoringModel item2 = this._repository.save(item);
				id = item2.getId();
				message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
						: " Se guardaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			} else {
				this._repository.save(item);
				message += "Se actualizaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			}			
			ResponseEntity response = new ResponseEntity();
			response.setExtra(id.toString());
			response.setMessage(message);
			response.setSuccess(success);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());			
		}
	}
	
	public ResponseEntity<MonitoringModel> searchByAgreement(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<MonitoringModel> response = new ResponseEntity<MonitoringModel>();			
			List<ActivityModel> activities = this._activityRepository.searchByMonitoringAndAgreement(id);
			List<Integer> monitoringIds = new ArrayList<Integer>();
			//activities.forEach(activity -> {
			//	if (!monitoringIds.contains(activity.getMonitoring().getId2()))
			//		monitoringIds.add(activity.getMonitoring().getId2());				
			//});
			//List<MonitoringModel> items = new ArrayList<MonitoringModel>();
			//for (int monitoringId : monitoringIds) {
			//	MonitoringModel item = this._repository.findById(monitoringId).get();
			//	items.add(item);
			//}
			//for (MonitoringModel item : items) {
			//	List<ActivityModel> activitiesFound = new ArrayList<ActivityModel>();
			//	int values = 0;
			//	int goals = 0;
			//	for (ActivityModel activity : activities) {
			//		if (activity.getMonitoring().getId2() == item.getId2()) {
			//			values += activity.getValue();
			//			goals += activity.getGoal();
			//			activitiesFound.add(activity);
			//		}
			//	}
			//	for (ActivityModel activity : activities) {
			//		activity.getCommitment().setProgress(values, goals);
			//	}
			//	item.setActivities(activitiesFound);
			//}
			response.setSuccess(success);
			//response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}