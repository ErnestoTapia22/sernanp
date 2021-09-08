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
import pe.sernanp.simrac.model.ModuleModel;
import pe.sernanp.simrac.model.UserModel;
import pe.sernanp.simrac.service.ModuleService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/module/")
public class ModuleController extends BaseController<ModuleModel, ModuleService> {
		
	@Autowired
	private ModuleService _service;
	
	@RequestMapping(value = "/search/{id}/{id2}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<ModuleModel> search(@PathVariable("id") int id,@PathVariable("id2") int id2) throws IOException {
		try {
			ResponseEntity<ModuleModel> response = this._service.search(id,id2);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}	

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
}
