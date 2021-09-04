package pe.sernanp.simrac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.gisriv.entity.ResponseEntity;
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
	
}
