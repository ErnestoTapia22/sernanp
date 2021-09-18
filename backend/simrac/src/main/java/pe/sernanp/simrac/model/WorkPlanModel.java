package pe.sernanp.simrac.model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class WorkPlanModel extends BaseModel {
	
	private int _year;
	private int _version;
	private Boolean _active;
	private ConservationAgreementModel _conservationAgreement;
	private List<ActivityModel> _activities;
	
	public int getYear () {
		return _year;
	}
	
	public void setYear (int year) {
		_year = year;
	}
	
	public int getVersion () {
		return _version;
	}
	
	public void setVersion (int version) {
		_version = version;
	}
	
	public Boolean getActive () {
		return _active;
	}
	
	public void setActive (Boolean active) {
		_active = active;
	}
	
	public ConservationAgreementModel getConservationAgreement() {
		return _conservationAgreement;
	}
		
	public void setConservationAgreement (ConservationAgreementModel conservationAgreement) {
		_conservationAgreement = conservationAgreement;		
	}
	
	public List<ActivityModel> getActivities() {
		return _activities;
	}
		
	public void setActivities(List<ActivityModel> activities) {
		_activities = activities;		
	}
}
