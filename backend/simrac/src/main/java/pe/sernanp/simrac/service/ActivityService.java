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
public class ActivityService extends BaseService<ActivityModel>{
	
	@Autowired
	private ActivityRepository _repository;
	
	public ResponseEntity<ActivityModel> searchByCommitment(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<ActivityModel> response = new ResponseEntity<ActivityModel>();			
			List<ActivityModel> items = this._repository.searchByCommitment(this._dataSource, id);
			response.setSuccess(success);
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	
}