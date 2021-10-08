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
import pe.sernanp.simrac.model.MasterPlanModel;
import pe.sernanp.simrac.model.ObjetiveModel;
import pe.sernanp.simrac.service.MasterPlanService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping ("/api/masterplan")
public class MasterPlanController extends BaseController {

	@Autowired
	private MasterPlanService _service;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	private ResponseEntity<?> save (@RequestBody MasterPlanModel item) {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}		
	}		
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<MasterPlanModel> detail(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<MasterPlanModel> response = this._service.detail(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchobjetives/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<ObjetiveModel> searchActionLines(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<ObjetiveModel> response = this._service.searchObjetives(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/searchbyanp/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<MasterPlanModel> searchByANP(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<MasterPlanModel> response = this._service.searchByANP(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	
}