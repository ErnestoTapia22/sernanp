package pe.sernanp.simrac.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.ObjetiveModel;
import pe.sernanp.simrac.model.WorkPlanModel;
import pe.sernanp.simrac.service.ActivityService;
import pe.sernanp.simrac.service.WorkPlanService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/activity/")
public class ActivityController extends BaseController<ActivityModel, ActivityService>{
	
	@Autowired
	private ActivityService _service;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchbycommitment/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<ActivityModel> searchByCommitment(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<ActivityModel> response = this._service.searchByCommitment(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
}