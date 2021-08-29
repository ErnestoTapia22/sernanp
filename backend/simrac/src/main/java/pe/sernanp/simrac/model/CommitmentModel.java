package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class CommitmentModel extends BaseModel {

	private ConservationAgreementModel _conservationagreement;
	
	public ConservationAgreementModel getConservationAgreement() {
		return _conservationagreement;
	}
		
	public void setConservationAgreement (ConservationAgreementModel conservationagreement) {
		_conservationagreement = conservationagreement;
		
	}
		
}
