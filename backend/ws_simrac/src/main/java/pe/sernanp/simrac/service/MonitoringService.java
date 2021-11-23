package pe.sernanp.simrac.service;
import java.util.ArrayList;
import java.util.Collections;
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
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.AnswerModel;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.MonitoringModel;
import pe.sernanp.simrac.model.ObjetiveModel;
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
	
	//public ResponseEntity save (MonitoringModel item) throws Exception{
	//	try {
	//		Integer id = item.getId();
	//		String message = "";
	//		boolean success = false;
	//		int rowsAffected = 0;
	//		item.setRegistrationDate(item.getRegistrationDate());
	//		if (id == 0) {
	//			MonitoringModel item2 = this._repository.save(item);
	//			id = item2.getId();
	//			message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
	//					: " Se guardaron sus datos de manera correcta";
	//			success = (id == 0) ? false : true;
	//		} else {
	//			this._repository.save(item);
	//			message += "Se actualizaron sus datos de manera correcta";
	//			success = (id == 0) ? false : true;
	//		}			
	//		ResponseEntity response = new ResponseEntity();
	//		response.setExtra(id.toString());
	//		response.setMessage(message);
	//		response.setSuccess(success);
	//		return response;
	//	} catch (Exception ex) {
	//		throw new Exception(ex.getMessage());			
	//	}
	//}
	
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
				
				LinkedHashMap<String,Integer> commitmentMap = new LinkedHashMap<String, Integer>();
				LinkedHashMap<String,Integer> actionLineMap = new LinkedHashMap<String, Integer>();
				LinkedHashMap<String,Integer> objetiveMap = new LinkedHashMap<String, Integer>();
				
				Collections.sort(activities, (f1, f2)->{
					return f1.getActivity().getCommitment().getActionLine().getObjetive().getDescription().compareTo( f2.getActivity().getCommitment().getActionLine().getObjetive().getDescription() );
				});
				
				
				
				List<ActivityDTO> itemActivies = new ArrayList<ActivityDTO>();
				
				LinkedHashMap<String,Double> commitmetProgress = new LinkedHashMap<String, Double>();
				
				activities.forEach(activity -> {
					ActivityDTO itemActivity = new ActivityDTO();
					itemActivity.setId(activity.getActivity().getId());
					itemActivity.setState(activity.getState());
					itemActivity.setName(activity.getActivity().getName());
					itemActivity.setIndicator(activity.getActivity().getIndicator());
					itemActivity.setGoal(activity.getActivity().getGoal());
					itemActivity.setSemester(activity.getActivity().getSemester());
					itemActivity.setValue(activity.getValue());
					itemActivity.setCommitment(activity.getActivity().getCommitment());
					itemActivity.setWorkPlan(activity.getActivity().getWorkPlan());				
					itemActivity.setId(activity.getActivity().getId());
					
					CommitmentModel commitment = new CommitmentModel();
					commitment.setId(activity.getActivity().getCommitment().getId());
					commitment.setDescription(activity.getActivity().getCommitment().getDescription());
					commitment.setActionLine(new ActionLineModel());
					commitment.getActionLine().setId(activity.getActivity().getCommitment().getActionLine().getId());
					commitment.getActionLine().setName(activity.getActivity().getCommitment().getActionLine().getName());
					commitment.getActionLine().setObjetive(new ObjetiveModel());
					commitment.getActionLine().getObjetive().setId(activity.getActivity().getCommitment().getActionLine().getObjetive().getId());
					commitment.getActionLine().getObjetive().setDescription(activity.getActivity().getCommitment().getActionLine().getObjetive().getDescription());
					commitment.setAllied(activity.getActivity().getCommitment().getAllied());
					if (commitmetProgress.containsKey("activity"+activity.getActivity().getCommitment().getId()))
						commitmetProgress.put("activity"+ activity.getActivity().getCommitment().getId(), (commitmetProgress.get("activity"+ activity.getActivity().getCommitment().getId()).doubleValue() +  itemActivity.getProgress() ) /2);
					else 
						commitmetProgress.put("activity"+ activity.getActivity().getCommitment().getId(), itemActivity.getProgress());
					
					if (commitmentMap.containsKey(""+activity.getActivity().getCommitment().getId()))
						commitmentMap.put(""+ activity.getActivity().getCommitment().getId(), (commitmentMap.get(""+ activity.getActivity().getCommitment().getId()).intValue() +  1 ));
					else
						commitmentMap.put(""+activity.getActivity().getCommitment().getId(), 1);	
					
					if (actionLineMap.containsKey(""+activity.getActivity().getCommitment().getActionLine().getId()))
						actionLineMap.put(""+ activity.getActivity().getCommitment().getActionLine().getId(), (actionLineMap.get(""+ activity.getActivity().getCommitment().getActionLine().getId()).intValue() +  1 ));
					else
						actionLineMap.put(""+activity.getActivity().getCommitment().getActionLine().getId(), 1);
					
					if (objetiveMap.containsKey(""+activity.getActivity().getCommitment().getActionLine().getObjetive().getId()))
						objetiveMap.put(""+ activity.getActivity().getCommitment().getActionLine().getObjetive().getId(), (objetiveMap.get(""+ activity.getActivity().getCommitment().getActionLine().getObjetive().getId()).intValue() +  1 ));
					else
						objetiveMap.put(""+activity.getActivity().getCommitment().getActionLine().getObjetive().getId(), 1);
					
					itemActivity.setCommitment(commitment);
					itemActivies.add(itemActivity);
					
				});		
				System.out.println("commitment");
				commitmentMap.forEach((k,v) -> 
					System.out.println("Key: " + k + ": Value: " + v)
				);
				System.out.println("action line");
				actionLineMap.forEach((k,v) -> 
					System.out.println("Key: " + k + ": Value: " + v)
				);
				System.out.println("objetive");
				objetiveMap.forEach((k,v) -> 
					System.out.println("Key: " + k + ": Value: " + v)
				);
				LinkedHashMap<String,Integer> commitmentMap2 = new LinkedHashMap<String, Integer>();
				LinkedHashMap<String,Integer> actionLineMap2 = new LinkedHashMap<String, Integer>();
				LinkedHashMap<String,Integer> objetiveMap2 = new LinkedHashMap<String, Integer>();
				
				itemActivies.forEach(activity -> {
					
					double value = commitmetProgress.get("activity"+ activity.getCommitment().getId()).doubleValue();					
					activity.getCommitment().setProgress(value);
					
					if (commitmentMap.containsKey(""+activity.getCommitment().getId())) {
						if (!commitmentMap2.containsKey(""+activity.getCommitment().getId())) {
							activity.getCommitment().setRowspan(commitmentMap.get(""+ activity.getCommitment().getId()).intValue());
							commitmentMap2.put(""+activity.getCommitment().getId(), activity.getCommitment().getId());
						}
					}
					if (actionLineMap.containsKey(""+activity.getCommitment().getActionLine().getId())) {
						if (!actionLineMap2.containsKey(""+activity.getCommitment().getActionLine().getId())) {
							activity.getCommitment().getActionLine().setRowspan(actionLineMap.get(""+ activity.getCommitment().getActionLine().getId()).intValue());
							actionLineMap2.put(""+activity.getCommitment().getActionLine().getId(), activity.getCommitment().getActionLine().getId());
						}
					}
					if (objetiveMap.containsKey(""+activity.getCommitment().getActionLine().getObjetive().getId())) {
						if (!objetiveMap2.containsKey(""+activity.getCommitment().getActionLine().getObjetive().getId())) {						
							activity.getCommitment().getActionLine().getObjetive().setRowspan(objetiveMap.get(""+ activity.getCommitment().getActionLine().getObjetive().getId()).intValue());
							objetiveMap2.put(""+activity.getCommitment().getActionLine().getObjetive().getId(), activity.getCommitment().getActionLine().getObjetive().getId());
						}
					}	
					
				});			
				item.setActivities(itemActivies);				
			});
			response.setSuccess(success);
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}