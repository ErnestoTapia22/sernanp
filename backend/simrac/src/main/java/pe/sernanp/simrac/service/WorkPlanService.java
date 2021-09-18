package pe.sernanp.simrac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.WorkPlanModel;
import pe.sernanp.simrac.repository.ActivityRepository;
import pe.sernanp.simrac.repository.WorkPlanRepository;

@Service
public class WorkPlanService extends BaseService<WorkPlanModel>{

	@Autowired
	private WorkPlanRepository _repository;
	
	@Autowired
	private ActivityRepository _activityRepository;
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity save(WorkPlanModel item) throws Exception {		
		TransactionDefinition definition = null;
		TransactionStatus status = null;		
		try {
			Integer id = item.getId2();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);
			id = this._repository.insert(this._dataSource, item);
			message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
					: " Se guardaron sus datos de manera correcta";
			success = (id == 0) ? false : true;
			Object id2 = id;
			item.getActivities().forEach( (activity) -> {
				//activity.setWorkPlan(new WorkPlanModel());
				//activity.getWorkPlan().setId(id);
				activity.setWorkPlan(new WorkPlanModel());
				activity.getWorkPlan().setId(id2);
				try {
					this._activityRepository.insert(this._dataSource, activity);
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
		
	public ResponseEntity<WorkPlanModel> searchByAgreement(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;			
			ResponseEntity<WorkPlanModel> response = new ResponseEntity<WorkPlanModel>();			
			List<ActivityModel> items = this._activityRepository.searchByAgreement(this._dataSource, id);			
			WorkPlanModel item = new WorkPlanModel();
			item.setActivities(items);
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity delete(int id) throws Exception {
		TransactionDefinition definition = null;
		TransactionStatus status = null;
		try {
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);
			Integer rowsAffected = this._repository.delete(this._dataSource, id);
			this.transactionManager.commit(status);
			ResponseEntity response = new ResponseEntity();
			response.setMessage("Se ha eliminado correctamente");
			response.setSuccess(true);
			return response;
		} catch (Exception ex) {
			if (this.transactionManager != null) {
				if (status != null)
					this.transactionManager.rollback(status);
			}
			throw new Exception(ex.getMessage());
		}
	}	
}