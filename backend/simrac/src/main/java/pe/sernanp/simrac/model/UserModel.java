package pe.sernanp.simrac.model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class UserModel extends BaseModel {
	
	private int _system;
	private List<ModuleModel> _modules;
	private RoleModel _role;
	private String _userName;
	private String _lastName;
	private String _documentNumber;
	private String _token;
	
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
	
	public RoleModel getRole () {
		return _role;
	}
	
	public void setRole(RoleModel role) {
		_role = role;
	}
	
	public String getUserName() {
		return _userName;
	}
	
	public void setUserName(String userName) {
		_userName = userName;
	}
	
	public String getLastName() {
		return _lastName;
	}
	
	public void setLastName(String lastName) {
		_lastName = lastName;
	}
	
	public String getDocumentNumber() {
		return _documentNumber;
	}
	
	public void setDocumentNumber(String documentNumber) {
		_documentNumber = documentNumber;
	}
	
	public String getToken() {
		return _token;
	}
	
	public void setToken(String token) {
		_token = token;
	}
}
