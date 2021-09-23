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
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.model.ComponentModel;
import pe.sernanp.simrac.service.ComponentService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/component")
public class ComponentController extends BaseController<ComponentModel, ComponentService> {

	@Autowired
	private ComponentService _service;
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEntity<ComponentModel> list() {
		ResponseEntity<ComponentModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody ComponentModel item) throws IOException {
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
	public ResponseEntity<ComponentModel> detail(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<ComponentModel> response = this._service.detail(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}		
}