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
import pe.sernanp.simrac.model.MonitoringModel;
import pe.sernanp.simrac.service.MonitoringService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping ("/api/monitoring")
public class MonitoringController extends BaseController {

	@Autowired
	private MonitoringService _service;	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	private ResponseEntity<?> save (@RequestBody MonitoringModel item) {
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
	public ResponseEntity<MonitoringModel> searchByAgreement(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<MonitoringModel> response = this._service.searchByAgreement(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}	
}