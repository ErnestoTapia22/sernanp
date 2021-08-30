package pe.sernanp.simrac.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.RoleModel;
import pe.sernanp.simrac.service.RoleService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/role")
public class RoleController extends BaseController<RoleModel, RoleService> {
	
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

}