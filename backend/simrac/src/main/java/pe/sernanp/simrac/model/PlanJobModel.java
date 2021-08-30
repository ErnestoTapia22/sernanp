package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class PlanJobModel extends BaseModel {

	private int _year;
	private int _version;
	private boolean _active;
	
	public int getYear() {
		return _year;
	}

	public void setYear(int year) {
		this._year = year;
	}
	
	public int getVersion() {
		return _version;
	}

	public void setVersion(int version) {
		this._version = version;
	}

	public boolean getActive() {
		return _active;
	}

	public void setActive(boolean active) {
		this._active = active;
	}
}
