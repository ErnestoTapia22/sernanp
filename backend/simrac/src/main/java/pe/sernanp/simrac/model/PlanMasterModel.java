package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class PlanMasterModel extends BaseModel {

	private AnpModel _anp;
	private int _version;
	private boolean _active;

	public AnpModel getAnp() {
		return _anp;
	}

	public void setAnp(AnpModel anp) {
		this._anp = anp;
	}
	
	public int getVersion() {
		return _version;
	}

	public void setVersion(int _version) {
		this._version = _version;
	}

	public boolean getActive() {
		return _active;
	}

	public void setActive(boolean _active) {
		this._active = _active;
	}
}
