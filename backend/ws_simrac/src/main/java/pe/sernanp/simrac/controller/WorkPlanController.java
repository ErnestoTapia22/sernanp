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

import pe.sernanp.simrac.dto.WorkPlanDTO;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.WorkPlanModel;
import pe.sernanp.simrac.service.WorkPlanService;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping ("/api/workplan")
public class WorkPlanController extends BaseController {

	@Autowired
	private WorkPlanService _service;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	private ResponseEntity<?> save (@RequestBody WorkPlanModel item) {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}		
	}
	
	@RequestMapping(value = "/searchbyagreement/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<WorkPlanModel> search(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<WorkPlanModel> response = this._service.search(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}	
	
	@RequestMapping(value = "/save2", method = RequestMethod.POST)
	@ResponseBody()
	private ResponseEntity<?> save2 (@RequestBody WorkPlanDTO item) {
		try {
			ResponseEntity<?> response = this._service.save2(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}		
	}
}