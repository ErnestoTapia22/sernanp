package pe.sernanp.simrac.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.model.ModuleModel;
import pe.sernanp.simrac.model.SourceModel;
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
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<UserModel> search(@RequestParam("item") String item) throws IOException {
		try {
			PaginatorEntity paginador = super.setPaginator();
			UserModel item2 = super.fromJson(item, UserModel.class);
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
	public ResponseEntity<?> save(@RequestBody UserModel item) throws IOException {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
}
