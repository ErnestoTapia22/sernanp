package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class WorkPlanModel extends BaseModel {
	
	private int _year;
	private int _version;
	private Boolean _active;
	
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
	
}
