package pe.sernanp.simrac.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.AlliedModel;
import pe.sernanp.simrac.service.AlliedService;

@RestController
@RequestMapping ("/api/allied")
public class AlliedController extends BaseController  {

	@Autowired
	private AlliedService _service;
	
	@RequestMapping(value = "/list")
	@GetMapping
	private ResponseEntity<AlliedModel> List () {
		ResponseEntity<AlliedModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;	
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	private ResponseEntity<?> save (@RequestBody AlliedModel item) {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}		
	}
		
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody()
	private ResponseEntity<?> delete (@PathVariable("id") int id) {
		try {
			ResponseEntity<?> response = this._service.delete(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
		
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody()
	private ResponseEntity<AlliedModel> detail (@PathVariable ("id") int id) {
		try {
			ResponseEntity<AlliedModel> response = this._service.detail(id);
			return response;
		} catch(Exception ex) {
			return super.getJSON(ex);
		}
	}	
	
	@RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<AlliedModel> search(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<AlliedModel> response = this._service.searchByAgreement(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
}