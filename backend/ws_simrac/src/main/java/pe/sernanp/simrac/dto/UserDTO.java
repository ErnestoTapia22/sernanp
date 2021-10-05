package pe.sernanp.simrac.dto;

import java.util.List;
import pe.sernanp.simrac.model.ModuleModel;

public class UserDTO {
	
	private int id;
	
	private String name;
	
	private List<ModuleModel> modules;	
	
	private int system;
	
	private String token;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ModuleModel> getModules() {
		return modules;
	}

	public void setModules(List<ModuleModel> modules) {
		this.modules = modules;
	}

	public int getSystem() {
		return system;
	}

	public void setSystem(int system) {
		this.system = system;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
