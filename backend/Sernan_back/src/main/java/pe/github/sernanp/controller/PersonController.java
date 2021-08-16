package pe.github.sernanp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.github.sernanp.entity.ResponseEntity;
import pe.github.sernanp.model.PersonModel;
import pe.github.sernanp.service.PersonService;

@RestController
@RequestMapping(value = "/persona/")
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
