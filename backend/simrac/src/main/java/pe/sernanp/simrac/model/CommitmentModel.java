package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class CommitmentModel extends BaseModel {

	private ConservationAgreementModel _conservationagreement;
	private ActionLineModel _actionline;
	private AlliedModel _allied;	
	private String _indicator;
	private Boolean _active;
	
	private double _progress;
	
	public ConservationAgreementModel getConservationAgreement() {
		return _conservationagreement;
	}
		
	public void setConservationAgreement (ConservationAgreementModel conservationagreement) {
		_conservationagreement = conservationagreement;		
	}
		
	public ActionLineModel getActionLine () {
		return _actionline;
	}
	
	public void setActionLine (ActionLineModel actionline) {
		_actionline = actionline;
	}
	
	public AlliedModel getAllied () {
		return _allied;
	}
	
	public void setAllied (AlliedModel allied) {
		_allied = allied;
	}
	
	public String getIndicator () {
		return _indicator;
	}
	
	public void setIndicator (String indicator) {
		_indicator = indicator;
	}
	
	public Boolean getActive () {
		return _active;
	}
	
	public void setActive (Boolean active) {
		_active = active;
	}
	
	public double getProgress () {
		return _progress;
	}
	
	public void setProgress (int value, int goal) {
		_progress += (value * 100.00 ) / goal;
	}
}
