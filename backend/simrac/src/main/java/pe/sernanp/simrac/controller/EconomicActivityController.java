package pe.sernanp.simrac.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.EconomicActivityModel;
import pe.sernanp.simrac.service.EconomicActivityService;

@RestController
@RequestMapping(value = "/api/economicactivity")
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
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<EconomicActivityModel> search(@RequestParam("item") String item) throws IOException {
		try {
			PaginatorEntity paginador = super.setPaginator();
			EconomicActivityModel item2 = super.fromJson(item, EconomicActivityModel.class);
			ResponseEntity<EconomicActivityModel> response = _service.search(item2, paginador);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody EconomicActivityModel item) throws IOException {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/save2", method = RequestMethod.POST )
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody String name) throws IOException {
		try {
			ResponseEntity<?> response = null;
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