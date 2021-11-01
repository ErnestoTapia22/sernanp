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
import pe.sernanp.simrac.model.ModuleModel;
import pe.sernanp.simrac.model.UserModel;
import pe.sernanp.simrac.service.AnpService;
import pe.sernanp.simrac.service.ModuleService;
import pe.sernanp.simrac.service.UserService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping ("/api/module")
public class ModuleController extends BaseController {
	
	@Autowired
	private ModuleService _service;		

	@RequestMapping(value = "/searchbysystem/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<ModuleModel> search(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<ModuleModel> response = this._service.searchBySystem(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/searchbyrole/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<ModuleModel> searchByRole(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<ModuleModel> response = this._service.searchByRole(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
}
