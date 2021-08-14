package pe.github.sernanp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.github.sernanp.entity.ResponseEntity;
import pe.github.sernanp.model.EconomicActivityModel;
import pe.github.sernanp.service.EconomicActivityService;

@RestController
@RequestMapping(value = "/economicactivity/")
public class EconomicActivityController extends BaseController<EconomicActivityModel, EconomicActivityService> {

	@Autowired
	private EconomicActivityService _service;
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEntity<EconomicActivityModel> list() {
		ResponseEntity<EconomicActivityModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}

	@RequestMapping(value = "/search2")
	@ResponseBody
	public ResponseEntity<EconomicActivityModel> search2(EconomicActivityModel item ) {
		ResponseEntity<EconomicActivityModel> response = new ResponseEntity<>();
		try {
			response = this._service.search(item,null);
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}
	@RequestMapping(value = "/search")
	@ResponseBody
	public ResponseEntity<EconomicActivityModel> search() {
		ResponseEntity<EconomicActivityModel> response = new ResponseEntity<>();
		try {
			response = this._service.find();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}
	@RequestMapping(value = "/findby", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> findBy(@RequestBody EconomicActivityModel item) throws IOException {
		try {
			ResponseEntity<EconomicActivityModel> response = this._service.findBy(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody EconomicActivityModel item) throws IOException {
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
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<EconomicActivityModel> detail(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<EconomicActivityModel> response = this._service.detail(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
}
