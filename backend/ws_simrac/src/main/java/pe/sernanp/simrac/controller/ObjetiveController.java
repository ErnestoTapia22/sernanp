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
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.ObjetiveModel;
import pe.sernanp.simrac.service.ObjetiveService;

@RestController
@RequestMapping ("/api/objetive")
public class ObjetiveController extends BaseController {

	@Autowired
	private ObjetiveService _service;
	
	@RequestMapping(value = "/list")
	@GetMapping
	private ResponseEntity<ObjetiveModel> List () {
		ResponseEntity<ObjetiveModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;	
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	private ResponseEntity<?> save (@RequestBody ObjetiveModel item) {
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
	private ResponseEntity<ObjetiveModel> detail (@PathVariable ("id") int id) {
		try {
			ResponseEntity<ObjetiveModel> response = this._service.detail(id);
			return response;
		} catch(Exception ex) {
			return super.getJSON(ex);
		}
	}
		
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchactionlines/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<ActionLineModel> searchActionLines(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<ActionLineModel> response = this._service.searchActionLines(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
}