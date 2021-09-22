package pe.sernanp.simrac.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.MonitoringModel;
import pe.sernanp.simrac.repository.ActivityRepository;
import pe.sernanp.simrac.repository.MonitoringRepository;

@Service
public class MonitoringService extends BaseService<MonitoringModel>{

	@Autowired
	private MonitoringRepository _repository;
	
	@Autowired
	private ActivityRepository _activityRepository;
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity save(MonitoringModel item) throws Exception {		
		TransactionDefinition definition = null;
		TransactionStatus status = null;		
		try {
			Integer id = item.getId2();
			String message = "";
			boolean success = true;
			int rowsAffected = 0;
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);
			item.setActive(success);
			item.setState(success);
			id = this._repository.insert(this._dataSource, item);
			message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
					: " Se guardaron sus datos de manera correcta";
			//success = (id == 0) ? false : true;
			Integer id2 = id;
			item.getActivities().forEach( (activity) -> {
				try {
					this._repository.insertAnswer(this._dataSource, activity.getId2(), id2, activity.getValue(), activity.getRegistrationDate(), success);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//throw new Exception("El código ingresado ya se encuentra registrado.");
					e.printStackTrace();					
				}
			});			
			this.transactionManager.commit(status);
			ResponseEntity respuesta = new ResponseEntity();
			respuesta.setExtra(id.toString());
			respuesta.setMessage(message);
			respuesta.setSuccess(success);
			return respuesta;
		} catch (Exception ex) {
			if (this.transactionManager != null) {
				if (status != null)
					this.transactionManager.rollback(status);
			}
			if (ex instanceof org.springframework.dao.DuplicateKeyException)
				throw new Exception("El código ingresado ya se encuentra registrado.");
			else
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
			List<ActivityModel> activities = this._activityRepository.searchByMonitoringAndAgreement(this._dataSource, id);
			List<Integer> monitoringIds = new ArrayList<Integer>();
			activities.forEach(activity -> {
				if (!monitoringIds.contains(activity.getMonitoring().getId2()))
					monitoringIds.add(activity.getMonitoring().getId2());				
			});
			List<MonitoringModel> items = new ArrayList<MonitoringModel>();
			for (int monitoringId : monitoringIds) {
				MonitoringModel item = this._repository.detail(this._dataSource, monitoringId);
				items.add(item);
			}
			for (MonitoringModel item : items) {
				List<ActivityModel> activitiesFound = new ArrayList<ActivityModel>();
				int values = 0;
				int goals = 0;
				for (ActivityModel activity : activities) {
					if (activity.getMonitoring().getId2() == item.getId2()) {
						values += activity.getValue();
						goals += activity.getGoal();
						activitiesFound.add(activity);
					}
				}
				for (ActivityModel activity : activities) {
					activity.getCommitment().setProgress(values, goals);
				}
				item.setActivities(activitiesFound);
			}
			response.setSuccess(success);
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}