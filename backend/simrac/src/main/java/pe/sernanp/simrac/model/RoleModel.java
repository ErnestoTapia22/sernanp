package pe.sernanp.simrac.model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class RoleModel extends BaseModel {
	
	private int _system;
	private String _flag;
	private List<ModuleModel> _modules;
	
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
	
	public List<ModuleModel>getModules () {
		return _modules;
	}
	
	public void setModules(List<ModuleModel> value) {
		_modules = value;
	}
}
