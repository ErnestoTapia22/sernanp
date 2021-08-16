package pe.github.sernanp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.github.sernanp.entity.PaginatorEntity;
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
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<ConservationAgreementModel> search(@RequestParam("item") String item) throws IOException {
		try {
			PaginatorEntity paginador = super.setPaginator();
			ConservationAgreementModel item2 = super.fromJson(item, ConservationAgreementModel.class);
			ResponseEntity<ConservationAgreementModel> response = _service.search(item2, paginador);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	

	/*@RequestMapping(value = "/search2")
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
	}*/
	
	@RequestMapping(value = "/findby", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> findBy(@RequestBody ConservationAgreementModel item) throws IOException {
		try {
			ResponseEntity<ConservationAgreementModel> response = this._service.findBy(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	
}
