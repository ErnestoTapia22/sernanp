package pe.sernanp.simrac.service;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.sernanp.simrac.dto.ActivityDTO;
import pe.sernanp.simrac.dto.MonitoringDTO;
import pe.sernanp.simrac.dto.WorkPlanDTO;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.AnswerModel;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.MonitoringModel;
import pe.sernanp.simrac.model.WorkPlanModel;
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
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity save2(MonitoringDTO item) throws Exception {		
		try {
			int id = item.getId();
			int id2 = 0;
			String message = "";
			boolean success = true;
			int rowsAffected = 0;
			item.setRegistrationDate(item.getRegistrationDate());
	
				MonitoringModel model2 = new MonitoringModel();
				model2.setId(item.getId());
				model2.setDescription(item.getDescription());
				model2.setRegistrationDate(item.getRegistrationDate());
				model2.setState(true);
				model2.setComment(item.getComment());
				model2.setAchievement(item.getAchievement());
				model2.setRecommendation(item.getRecommendation());
				model2.setActive(true);
				model2.setEvaluation(item.getEvaluation());
				
				MonitoringModel item2 = this._repository.save(model2);
				id = item2.getId();
				message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
						: " Se guardaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
				id2 = item2.getId();
				
	
				item.getActivities().forEach( (answer) -> {
					AnswerModel model3 = new AnswerModel();
					model3.setId(0);
					model3.setValue(answer.getValue());
					model3.setRegistrationDate(item2.getRegistrationDate());
					model3.setState(true);					
					model3.setActivity(new ActivityModel());
					model3.getActivity().setId(answer.getId());
					model3.setMonitoring(new MonitoringModel());
					model3.getMonitoring().setId(item2.getId());
					
					try {
						this._answerRepository.save(model3);
						
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
	
	public ResponseEntity<MonitoringModel> searchByAgreement(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<MonitoringModel> response = new ResponseEntity<MonitoringModel>();
			List<MonitoringModel> items = this._repository.searchByMonitoringAndAgreement(id);
			items.forEach(item -> {				
				List<AnswerModel> activities = this._answerRepository.searchByMonitoring(item.getId());				
				List<ActivityDTO> items2 = new ArrayList<ActivityDTO>();
				List<CommitmentModel> itemsC = new ArrayList<CommitmentModel>();
				LinkedHashMap<String,Double> s = new LinkedHashMap<String, Double>();
				activities.forEach(t -> {
					ActivityDTO itemActivity = new ActivityDTO();
					itemActivity.setId(t.getActivity().getId());
					itemActivity.setState(t.getState());
					itemActivity.setName(t.getActivity().getName());
					itemActivity.setIndicator(t.getActivity().getIndicator());
					itemActivity.setGoal(t.getActivity().getGoal());
					itemActivity.setSemester(t.getActivity().getSemester());
					itemActivity.setValue(t.getValue());
					itemActivity.setCommitment(t.getActivity().getCommitment());
					itemActivity.setWorkPlan(t.getActivity().getWorkPlan());
					//itemActivity.getCommitment().setProgress( t.getValue(), t.getActivity().getGoal() );					
					itemActivity.setId(t.getActivity().getId());
					items2.add(itemActivity);
					if (s.containsKey("activity"+t.getActivity().getCommitment().getId()))
						s.put("activity"+ t.getActivity().getCommitment().getId(), (s.get("activity"+ t.getActivity().getCommitment().getId()).doubleValue() +  itemActivity.getProgress() ) /2);
					else 
						s.put("activity"+ t.getActivity().getCommitment().getId(), itemActivity.getProgress());
				});	
				items2.forEach(t -> {
					double value = s.get("activity"+ t.getCommitment().getId()).doubleValue();					
					t.getCommitment().setProgress(value);
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