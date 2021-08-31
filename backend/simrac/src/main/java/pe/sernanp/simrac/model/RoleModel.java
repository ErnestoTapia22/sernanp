package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class RoleModel extends BaseModel {
	
	private int _system;
	private String _flag;
	
	public int getSystem() {
		return _system;
	}

	public void setSystem(int system) {
		_system = system;
	}
	
	public String getFlag() {
		return _flag;
	}

	public void setFlag(String flag) {
		_flag = flag;
	}
	
}
