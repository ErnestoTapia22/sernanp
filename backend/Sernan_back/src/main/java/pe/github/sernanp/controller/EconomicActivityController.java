package pe.github.sernanp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
