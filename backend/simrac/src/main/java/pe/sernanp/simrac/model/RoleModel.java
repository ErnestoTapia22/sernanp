package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class RoleModel extends BaseModel {
	
	private int _system;
	
	public int getSystem() {
		return _system;
	}

	public void setSystem(int system) {
		_system = system;
	}

}
