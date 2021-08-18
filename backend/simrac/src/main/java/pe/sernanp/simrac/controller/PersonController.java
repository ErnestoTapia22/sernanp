package pe.sernanp.simrac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.PersonModel;
import pe.sernanp.simrac.service.PersonService;

@RestController
@RequestMapping(value = "/persona")
public class PersonController extends BaseController<PersonModel, PersonService> {

	@Autowired
	private PersonService _service;	
		
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEntity<PersonModel> list() {
		ResponseEntity<PersonModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}
}
