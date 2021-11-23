package pe.sernanp.simrac.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jackson.map.util.ArrayBuilders.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import net.sf.jasperreports.engine.export.data.BooleanTextValue;
import pe.sernanp.simrac.dto.ActivityDTO;
import pe.sernanp.simrac.dto.WorkPlanDTO;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.ObjetiveModel;
import pe.sernanp.simrac.model.WorkPlanModel;
import pe.sernanp.simrac.repository.ActivityRepository;
import pe.sernanp.simrac.repository.WorkPlanRepository;

@Service
public class WorkPlanService {
	
	@Autowired
	private WorkPlanRepository _repository;
	
	@Autowired
	private ActivityRepository _activityRepository;

	//@Transactional
	//public ResponseEntity save (WorkPlanModel item) throws Exception{
	//	try {
	//		Integer id = item.getId();
	//		String message = "";
	//		boolean success = false;
	//		int rowsAffected = 0;			
	//		item.setRegistrationDate(item.getRegistrationDate());
	//		this._repository.updatePlanActive(item.getConservationAgreement().getId());
	//		if (id == 0) {
	//			WorkPlanModel item2 = this._repository.save(item);
	//			id = item2.getId();
	//			message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
	//					: " Se guardaron sus datos de manera correcta";
	//			success = (id == 0) ? false : true;
	//		} else {
	//			this._repository.save(item);
	//			message += "Se actualizaron sus datos de manera correcta";
	//			success = (id == 0) ? false : true;
	//		}
	//		
	//		ResponseEntity response = new ResponseEntity();
	//		response.setExtra(id.toString());
	//		response.setMessage(message);
	//		response.setSuccess(success);
	//		return response;
	//	} catch (Exception ex) {
	//		throw new Exception(ex.getMessage());
	//		
	//	}
	//}
	
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity save2(WorkPlanDTO item) throws Exception {		
		try {
			int id = item.getId();
			int id2 = 0;
			String message = "";
			boolean success = true;
			int rowsAffected = 0;
			this._repository.updatePlanActive(item.getConservationAgreement().getId());
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
				activity.setId(0);
				activity.setState(true);
				activity.setWorkPlan(new WorkPlanModel());
				activity.getWorkPlan().setId(item2.getId());
				activity.setRegistrationDate(activity.getRegistrationDate());
				activity.setActive(true);
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
		
	public ResponseEntity<WorkPlanDTO> search(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<WorkPlanDTO> response = new ResponseEntity<WorkPlanDTO>();			
			List<ActivityModel> items = this._activityRepository.searchByAgreement(id);			
			WorkPlanDTO item = new WorkPlanDTO();
			WorkPlanModel itemWorkPlan = this._repository.searchActive(id);
			if (itemWorkPlan == null)
			{
				response.setSuccess(false);
				return response;
			}
			LinkedHashMap<String,Integer> commitmentMap = new LinkedHashMap<String, Integer>();
			LinkedHashMap<String,Integer> actionLineMap = new LinkedHashMap<String, Integer>();
			LinkedHashMap<String,Integer> objetiveMap = new LinkedHashMap<String, Integer>();
			
			Collections.sort(items, (f1, f2)->{
				return f1.getCommitment().getActionLine().getObjetive().getDescription().compareTo( f2.getCommitment().getActionLine().getObjetive().getDescription() );
			});
			item.setName(itemWorkPlan.getName());
			item.setVersion(itemWorkPlan.getVersion());
			item.setYear(itemWorkPlan.getYear());			
			items.forEach(activity -> {			
				if (commitmentMap.containsKey(""+activity.getCommitment().getId()))
					commitmentMap.put(""+ activity.getCommitment().getId(), (commitmentMap.get(""+ activity.getCommitment().getId()).intValue() +  1 ));
				else
					commitmentMap.put(""+activity.getCommitment().getId(), 1);	
				
				if (actionLineMap.containsKey(""+activity.getCommitment().getActionLine().getId()))
					actionLineMap.put(""+ activity.getCommitment().getActionLine().getId(), (actionLineMap.get(""+ activity.getCommitment().getActionLine().getId()).intValue() +  1 ));
				else
					actionLineMap.put(""+activity.getCommitment().getActionLine().getId(), 1);
				
				if (objetiveMap.containsKey(""+activity.getCommitment().getActionLine().getObjetive().getId()))
					objetiveMap.put(""+ activity.getCommitment().getActionLine().getObjetive().getId(), (objetiveMap.get(""+ activity.getCommitment().getActionLine().getObjetive().getId()).intValue() +  1 ));
				else
					objetiveMap.put(""+activity.getCommitment().getActionLine().getObjetive().getId(), 1);
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

			List<ActivityModel> itemsActivies = new ArrayList<ActivityModel>();
			for (ActivityModel activity : items) {
				ActivityModel activityModel = new ActivityModel();
				activityModel.setId(activity.getId());
				activityModel.setIndicator(activity.getIndicator());
				activityModel.setName(activity.getName());
				activityModel.setSemester(activity.getSemester());
				activityModel.setGoal(activity.getGoal());
				activityModel.setWorkPlan(activity.getWorkPlan());
				CommitmentModel commitment = new CommitmentModel();
				commitment.setId(activity.getCommitment().getId());
				commitment.setDescription(activity.getCommitment().getDescription());
				commitment.setActionLine(new ActionLineModel());
				commitment.getActionLine().setName(activity.getCommitment().getActionLine().getName());
				commitment.getActionLine().setObjetive(new ObjetiveModel());
				commitment.getActionLine().getObjetive().setDescription(activity.getCommitment().getActionLine().getObjetive().getDescription());
				commitment.setAllied(activity.getCommitment().getAllied());
				if (commitmentMap.containsKey(""+activity.getCommitment().getId())) {
					if (!commitmentMap2.containsKey(""+activity.getCommitment().getId())) {
						commitment.setRowspan(commitmentMap.get(""+ activity.getCommitment().getId()).intValue());
						commitmentMap2.put(""+activity.getCommitment().getId(), activity.getCommitment().getId());
					}
				}
				if (actionLineMap.containsKey(""+activity.getCommitment().getActionLine().getId())) {
					if (!actionLineMap2.containsKey(""+activity.getCommitment().getActionLine().getId())) {
						commitment.getActionLine().setRowspan(actionLineMap.get(""+ activity.getCommitment().getActionLine().getId()).intValue());
						actionLineMap2.put(""+activity.getCommitment().getActionLine().getId(), activity.getCommitment().getActionLine().getId());
					}
				}
				if (objetiveMap.containsKey(""+activity.getCommitment().getActionLine().getObjetive().getId())) {
					if (!objetiveMap2.containsKey(""+activity.getCommitment().getActionLine().getObjetive().getId())) {						
						commitment.getActionLine().getObjetive().setRowspan(objetiveMap.get(""+ activity.getCommitment().getActionLine().getObjetive().getId()).intValue());
						objetiveMap2.put(""+activity.getCommitment().getActionLine().getObjetive().getId(), activity.getCommitment().getActionLine().getObjetive().getId());
					}
				}	
				activityModel.setCommitment(commitment);
				itemsActivies.add(activityModel);
			}
			item.setActivities(itemsActivies);
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public ResponseEntity<WorkPlanDTO> listByAgreement(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<WorkPlanDTO> response = new ResponseEntity<WorkPlanDTO>();			
			//List<ActivityModel> items = this._activityRepository.searchByAgreement(id);			
			WorkPlanDTO item = new WorkPlanDTO();
			
			List<WorkPlanModel> itemWorkPlans = this._repository.searchByAgreement(id);
			
			List<WorkPlanDTO> works = new ArrayList<WorkPlanDTO>(); 
			itemWorkPlans.forEach( t -> {
				
				WorkPlanDTO work = new WorkPlanDTO();
				work.setId(t.getId());
				work.setName(t.getName());
				work.setVersion(t.getVersion());
				work.setYear(t.getYear());
				
				List<ActivityModel> itemsActivity = this._activityRepository.searchByWorkPlan(t.getId());	
				
				LinkedHashMap<String,Integer> commitmentMap = new LinkedHashMap<String, Integer>();
				LinkedHashMap<String,Integer> actionLineMap = new LinkedHashMap<String, Integer>();
				LinkedHashMap<String,Integer> objetiveMap = new LinkedHashMap<String, Integer>();
				
				Collections.sort(itemsActivity, (f1, f2)->{
					return f1.getCommitment().getActionLine().getObjetive().getDescription().compareTo( f2.getCommitment().getActionLine().getObjetive().getDescription() );
				});
				
				//item.setName(itemWorkPlan.getName());
				//item.setVersion(itemWorkPlan.getVersion());
				//item.setYear(itemWorkPlan.getYear());
				itemsActivity.forEach(activity -> {			
					if (commitmentMap.containsKey(""+activity.getCommitment().getId()))
						commitmentMap.put(""+ activity.getCommitment().getId(), (commitmentMap.get(""+ activity.getCommitment().getId()).intValue() +  1 ));
					else
						commitmentMap.put(""+activity.getCommitment().getId(), 1);	
					
					if (actionLineMap.containsKey(""+activity.getCommitment().getActionLine().getId()))
						actionLineMap.put(""+ activity.getCommitment().getActionLine().getId(), (actionLineMap.get(""+ activity.getCommitment().getActionLine().getId()).intValue() +  1 ));
					else
						actionLineMap.put(""+activity.getCommitment().getActionLine().getId(), 1);
					
					if (objetiveMap.containsKey(""+activity.getCommitment().getActionLine().getObjetive().getId()))
						objetiveMap.put(""+ activity.getCommitment().getActionLine().getObjetive().getId(), (objetiveMap.get(""+ activity.getCommitment().getActionLine().getObjetive().getId()).intValue() +  1 ));
					else
						objetiveMap.put(""+activity.getCommitment().getActionLine().getObjetive().getId(), 1);
				});
				
				LinkedHashMap<String,Integer> commitmentMap2 = new LinkedHashMap<String, Integer>();
				LinkedHashMap<String,Integer> actionLineMap2 = new LinkedHashMap<String, Integer>();
				LinkedHashMap<String,Integer> objetiveMap2 = new LinkedHashMap<String, Integer>();

				List<ActivityModel> itemsActivies = new ArrayList<ActivityModel>();
				for (ActivityModel activity : itemsActivity) {
					ActivityModel activityModel = new ActivityModel();
					activityModel.setId(activity.getId());
					activityModel.setIndicator(activity.getIndicator());
					activityModel.setName(activity.getName());
					activityModel.setSemester(activity.getSemester());
					activityModel.setGoal(activity.getGoal());
					activityModel.setWorkPlan(activity.getWorkPlan());
					CommitmentModel commitment = new CommitmentModel();
					commitment.setId(activity.getCommitment().getId());
					commitment.setDescription(activity.getCommitment().getDescription());
					commitment.setActionLine(new ActionLineModel());
					commitment.getActionLine().setName(activity.getCommitment().getActionLine().getName());
					commitment.getActionLine().setObjetive(new ObjetiveModel());
					commitment.getActionLine().getObjetive().setDescription(activity.getCommitment().getActionLine().getObjetive().getDescription());
					commitment.setAllied(activity.getCommitment().getAllied());
					if (commitmentMap.containsKey(""+activity.getCommitment().getId())) {
						if (!commitmentMap2.containsKey(""+activity.getCommitment().getId())) {
							commitment.setRowspan(commitmentMap.get(""+ activity.getCommitment().getId()).intValue());
							commitmentMap2.put(""+activity.getCommitment().getId(), activity.getCommitment().getId());
						}
					}
					if (actionLineMap.containsKey(""+activity.getCommitment().getActionLine().getId())) {
						if (!actionLineMap2.containsKey(""+activity.getCommitment().getActionLine().getId())) {
							commitment.getActionLine().setRowspan(actionLineMap.get(""+ activity.getCommitment().getActionLine().getId()).intValue());
							actionLineMap2.put(""+activity.getCommitment().getActionLine().getId(), activity.getCommitment().getActionLine().getId());
						}
					}
					if (objetiveMap.containsKey(""+activity.getCommitment().getActionLine().getObjetive().getId())) {
						if (!objetiveMap2.containsKey(""+activity.getCommitment().getActionLine().getObjetive().getId())) {						
							commitment.getActionLine().getObjetive().setRowspan(objetiveMap.get(""+ activity.getCommitment().getActionLine().getObjetive().getId()).intValue());
							objetiveMap2.put(""+activity.getCommitment().getActionLine().getObjetive().getId(), activity.getCommitment().getActionLine().getObjetive().getId());
						}
					}	
					activityModel.setCommitment(commitment);
					itemsActivies.add(activityModel);
				}				
				work.setActivities(itemsActivies);
				works.add(work);
			});			
			response.setSuccess(success);
			response.setItems(works);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
}