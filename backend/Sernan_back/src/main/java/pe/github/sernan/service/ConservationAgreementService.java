package pe.github.sernan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.github.sernan.model.ConservationAgreement;
import pe.github.sernan.repository.ConservationAgreementRepository;


@Service
public class ConservationAgreementService {

	
	@Autowired
	private ConservationAgreementRepository conservationAgreementRepository;
	
	
	
	public java.util.List<ConservationAgreement> List() {
		return conservationAgreementRepository.List();
	} 
	
	
}
