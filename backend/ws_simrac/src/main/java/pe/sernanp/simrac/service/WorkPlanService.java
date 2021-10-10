package pe.sernanp.simrac.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import pe.sernanp.simrac.dto.WorkPlanDTO;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.WorkPlanModel;
import pe.sernanp.simrac.repository.ActivityRepository;
import pe.sernanp.simrac.repository.WorkPlanRepository;

@Service
public class WorkPlanService {
	
	@Autowired
	private WorkPlanRepository _repository;
	
	@Autowired
	private ActivityRepository _activityRepository;

	public ResponseEntity save (WorkPlanModel item) throws Exception{
		try {
			Integer id = item.getId();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			item.setRegistrationDate(item.getRegistrationDate());
			if (id == 0) {
				WorkPlanModel item2 = this._repository.save(item);
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
	
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity save2(WorkPlanDTO item) throws Exception {		
		try {
			int id = item.getId();
			int id2 = 0;
			String message = "";
			boolean success = true;
			int rowsAffected = 0;
			List<WorkPlanModel> workPlans = this._repository.searchByAgreement(item.getConservationAgreement().getId());
			item.setName("Plan de Trabajo N° " + (workPlans.size()+1));
			item.setRegistrationDate(item.getRegistrationDate());
			
				WorkPlanModel model2 = new WorkPlanModel();
				model2.setId(item.getId());
				model2.setConservationAgreement(item.getConservationAgreement());
				model2.setName(item.getName());
				model2.setDescription(item.getDescription());
				model2.setRegistrationDate(item.getRegistrationDate());
				model2.setState(true);
				model2.setYear(item.getYear());
				model2.setVersion(workPlans.size()+1);
				model2.setActive(true);				
							
				WorkPlanModel item2 = this._repository.save(model2);
				id = item2.getId();
				message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
						: " Se guardaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
				id2 = item2.getId();
						
			item.getActivities().forEach( (activity) -> {
				activity.setState(true);
				activity.setWorkPlan(new WorkPlanModel());
				activity.getWorkPlan().setId(item2.getId());
				activity.setRegistrationDate(activity.getRegistrationDate());
				try {
					this._activityRepository.save(activity);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//throw new Exception("El código ingresado ya se encuentra registrado.");
					e.printStackTrace();					
				}
			});
			ResponseEntity respuesta = new ResponseEntity();
			respuesta.setExtra(id + "");
			respuesta.setMessage(message);
			respuesta.setSuccess(success);
			return respuesta;
		} catch (Exception ex) {
				throw new Exception(ex.getMessage());
		}
	}	
	
	
	
	public ResponseEntity<WorkPlanModel> search(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<WorkPlanModel> response = new ResponseEntity<WorkPlanModel>();			
			List<ActivityModel> items = this._activityRepository.searchByAgreement(id);			
			WorkPlanModel item = new WorkPlanModel();
			//item.setActivities(items);
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	
	
}