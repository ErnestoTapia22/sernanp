package pe.sernanp.simrac.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.sernanp.simrac.dto.ActivityDTO;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.AnswerModel;
import pe.sernanp.simrac.model.MonitoringModel;
import pe.sernanp.simrac.repository.ActivityRepository;
import pe.sernanp.simrac.repository.AnswerRepository;
import pe.sernanp.simrac.repository.MonitoringRepository;

@Service
public class MonitoringService {
	
	@Autowired
	private MonitoringRepository _repository;
	
	@Autowired
	private AnswerRepository _answerRepository;
	
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
			List<MonitoringModel> items = this._repository.searchByMonitoringAndAgreement(id);
			items.forEach(item -> {
				List<AnswerModel> activities = this._answerRepository.searchByMonitoringAndAgreement(id);
				
				List<ActivityDTO> items2 = new ArrayList<ActivityDTO>();
				activities.forEach(t -> {
					ActivityDTO itemActivity = new ActivityDTO();
					itemActivity.setState(t.getState());
					itemActivity.setMeta(t.getActivity().getMeta());
					itemActivity.setSemester(t.getActivity().getSemester());					
					items2.add(itemActivity);
				});				
				item.setActivities(items2);
			});
			response.setSuccess(success);
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}