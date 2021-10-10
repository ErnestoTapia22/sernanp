package pe.sernanp.simrac.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.sernanp.simrac.dto.ConservationAgreementDTO;
import pe.sernanp.simrac.entity.PaginatorEntity;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.service.ConservationAgreementService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping ("/api/conservationagreement")
public class ConservationAgreementController extends BaseController {

	@Autowired
	private ConservationAgreementService _service;
	
	@RequestMapping(value = "/list")
	@GetMapping
	private ResponseEntity<ConservationAgreementModel> List () {
		ResponseEntity<ConservationAgreementModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;		
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	private ResponseEntity<?> save (@RequestBody ConservationAgreementModel item) {
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
	private ResponseEntity<ConservationAgreementModel> detail (@PathVariable ("id") int id) {
		try {
			ResponseEntity<ConservationAgreementModel> response = this._service.detail(id);
			return response;
		} catch(Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<ConservationAgreementModel> search(@RequestParam("item") String item) throws IOException {
		try {
			PaginatorEntity paginator = super.setPaginator();
			ConservationAgreementDTO item2 = super.fromJson(item, ConservationAgreementDTO.class);
			ResponseEntity<ConservationAgreementModel> response = this._service.search(item2, paginator);
			return response;
		} catch (Exception ex) {			
			return super.getJSON(ex);
		}
	}
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	@RequestMapping(value = "/search2", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<ConservationAgreementModel> search2(@RequestBody ConservationAgreementDTO item) throws IOException {
		try {
			ResponseEntity<ConservationAgreementModel> response = this._service.search2(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
}