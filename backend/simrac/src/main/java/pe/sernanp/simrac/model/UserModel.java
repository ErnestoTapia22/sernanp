package pe.sernanp.simrac.model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class UserModel extends BaseModel {
	
	private int _system;
	private List<ModuleModel> _modules;
		
	public int getSystem () {
		return _system;
	}
	
	public void setSystem (int value) {
		_system = value;
	}
	
	public List<ModuleModel>getModules () {
		return _modules;
	}
	
	public void setModules(List<ModuleModel> value) {
		_modules = value;
	}
}
