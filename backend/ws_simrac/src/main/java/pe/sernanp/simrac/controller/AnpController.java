package pe.sernanp.simrac.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.sernanp.simrac.entity.PaginatorEntity;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.service.ActionLineService;
import pe.sernanp.simrac.service.AnpService;

@RestController
@RequestMapping ("/api/anp")
public class AnpController extends BaseController {
	
	@Autowired
	private AnpService _service;
	
	@RequestMapping(value = "/list")
	@GetMapping
	private ResponseEntity<AnpModel> List () {
		ResponseEntity<AnpModel> response = new ResponseEntity<>();
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
	public ResponseEntity<AnpModel> search(@RequestParam("item") String item) throws IOException {
		try {
			PaginatorEntity paginador = super.setPaginator();
			AnpModel item2 = super.fromJson(item, AnpModel.class);
			ResponseEntity<AnpModel> response = null;
			return response;
		} catch (Exception ex) {
			return null;
		}
	}
}
