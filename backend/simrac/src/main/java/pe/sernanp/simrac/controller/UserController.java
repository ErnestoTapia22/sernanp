package pe.sernanp.simrac.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.UserModel;
import pe.sernanp.simrac.service.UserService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/user/")
public class UserController extends BaseController<UserModel, UserService> {

	
	@Autowired
	private UserService _service;
	
	
	@RequestMapping(value = "/validate/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<UserModel> validate(@PathVariable("id") String id) throws IOException {
		try {
			ResponseEntity<UserModel> response = this._service.validate(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	
}
