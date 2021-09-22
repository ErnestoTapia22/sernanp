package pe.sernanp.simrac.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.ObjetiveModel;
import pe.sernanp.simrac.service.ObjetiveService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/objetive/")
public class ObjetiveController extends BaseController<ObjetiveModel, ObjetiveService> {
	
	@Autowired
	private ObjetiveService _service;
		
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<ObjetiveModel> detail(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<ObjetiveModel> response = this._service.detail(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody ObjetiveModel item) throws IOException {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
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