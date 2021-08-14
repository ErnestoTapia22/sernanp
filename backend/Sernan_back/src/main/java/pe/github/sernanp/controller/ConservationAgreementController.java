package pe.github.sernanp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.github.sernanp.entity.ResponseEntity;
import pe.github.sernanp.model.ConservationAgreementModel;
import pe.github.sernanp.service.ConservationAgreementService;

@RestController
@RequestMapping(value = "/conservationagreement/")
public class ConservationAgreementController extends BaseController<ConservationAgreementModel, ConservationAgreementService>  {
	
	@Autowired
	private ConservationAgreementService _service;
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEntity<ConservationAgreementModel> list() {
		ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}
}
