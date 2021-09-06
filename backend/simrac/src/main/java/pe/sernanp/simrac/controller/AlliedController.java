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
import pe.sernanp.simrac.model.AlliedModel;
import pe.sernanp.simrac.service.AlliedService;
import pe.gisriv.entity.ResponseEntity;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/allied/")
public class AlliedController extends BaseController<AlliedModel, AlliedService> {

	@Autowired
	private AlliedService _service;
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody AlliedModel item) throws IOException {
		try {
			ResponseEntity<?> response = this._service.save(item);
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
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<AlliedModel> detail(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<AlliedModel> response = this._service.detail(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<AlliedModel> search(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<AlliedModel> response = this._service.searchByAgreement(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
}
