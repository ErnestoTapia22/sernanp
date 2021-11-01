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
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.service.ActivityService;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping ("/api/activity")
public class ActivityController extends BaseController {

	@Autowired
	private ActivityService _service;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	private ResponseEntity<?> save (@RequestBody ActivityModel item) {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}		
	}
	
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
