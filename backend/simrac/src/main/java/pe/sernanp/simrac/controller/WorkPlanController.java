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
import pe.sernanp.simrac.model.ObjetiveModel;
import pe.sernanp.simrac.model.WorkPlanModel;
import pe.sernanp.simrac.service.WorkPlanService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/workplan/")
public class WorkPlanController extends BaseController<WorkPlanModel, WorkPlanService>{
	
	@Autowired
	private WorkPlanService _service;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody WorkPlanModel item) throws IOException {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchbyagreement/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<WorkPlanModel> searchByAgreement(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<WorkPlanModel> response = this._service.searchByAgreement(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
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
}