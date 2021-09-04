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
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.AlliedModel;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.MasterPlanModel;
import pe.sernanp.simrac.model.ObjetiveModel;
import pe.sernanp.simrac.service.CommitmentService;
import pe.sernanp.simrac.service.MasterPlanService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/masterplan/")
public class MasterPlanController extends BaseController<MasterPlanModel, MasterPlanService> {
	
	@Autowired
	private MasterPlanService _service;
		
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<?> delete(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<?> response = this._service.delete(id);
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
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody MasterPlanModel item) throws IOException {
		try {
			ResponseEntity<?> response = this._service.save(item);
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
