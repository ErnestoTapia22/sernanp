package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class ExternalCommitmentModel extends BaseModel {

	private ConservationAgreementModel _conservationagreement;
	private String _objetive;
	private String _actionline;
	
	public ConservationAgreementModel getConservationAgreement() {
		return _conservationagreement;
	}
		
	public void setConservationAgreement (ConservationAgreementModel conservationagreement) {
		_conservationagreement = conservationagreement;
		
	}
	
	public String getObjetive () {
		return _objetive;
	}
	
	public void setObjetive (String objetive) {
		_objetive = objetive;
	}
	
	public String getActionLine () {
		return _actionline;
	}
	
	public void setActionLine (String actionline) {
		_actionline = actionline;
	}
		
}
