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
import pe.sernanp.simrac.dto.RoleDTO;
import pe.sernanp.simrac.dto.UserDTO;
import pe.sernanp.simrac.entity.PaginatorEntity;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.model.RoleModel;
import pe.sernanp.simrac.model.UserModel;
import pe.sernanp.simrac.service.AnpService;
import pe.sernanp.simrac.service.RoleService;
import pe.sernanp.simrac.service.UserService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping ("/api/role")
public class RoleController extends BaseController {
	
	@Autowired
	 private RoleService _service;
	 
	@RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<RoleModel> search(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<RoleModel> response = this._service.search(id);
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


	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody RoleModel item) throws IOException {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}

	@RequestMapping(value = "/save2", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save2(@RequestBody RoleDTO item) throws IOException {
		try {
			ResponseEntity<?> response = this._service.save2(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}	 
}
