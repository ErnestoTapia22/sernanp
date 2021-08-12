package pe.github.sernan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.github.sernan.model.ConservationAgreement;
import pe.github.sernan.service.ConservationAgreementService;

@RestController
@RequestMapping(value = "/conservationagreement/")
public class ConservationAgreementController {
	
	@Autowired
	private ConservationAgreementService conservationAgreementService;
	
	@RequestMapping(value = "list")
	public java.util.List<ConservationAgreement> List()
    {
		java.util.List<ConservationAgreement> ConservationAgreement = conservationAgreementService.List();
		return ConservationAgreement; //new ResponseEntity(persons, HttpStatus.OK);
    }

}
