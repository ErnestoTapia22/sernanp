package pe.sernanp.simrac.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.sernanp.simrac.dto.AnpDTO;
import pe.sernanp.simrac.dto.UserDTO;
import pe.sernanp.simrac.entity.PaginatorEntity;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.model.UserModel;
import pe.sernanp.simrac.service.AnpService;
import pe.sernanp.simrac.service.UserService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping ("/api/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserService _service;
	
	@RequestMapping(value = "/validate/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<UserDTO> validate(@PathVariable("id") String id) throws IOException {
		try {
			ResponseEntity<UserDTO> response = this._service.validate(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<UserModel> search(@RequestParam("item") String item) throws IOException {
		try {
			PaginatorEntity paginador = super.setPaginator();
			UserDTO item2 = super.fromJson(item, UserDTO.class);
			ResponseEntity<UserModel> response = this._service.search(item2, paginador);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/searchwithoutlogin/{dni}/{system}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<UserModel> search(@PathVariable("dni") String dni, @PathVariable("system") int system) throws IOException {
		try {
			ResponseEntity<UserModel> response = this._service.searchWithoutLogin(dni, system);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody UserDTO item) throws IOException {
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
}
