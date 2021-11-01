package pe.sernanp.simrac.dto;

import java.util.Date;
import java.util.List;
import pe.sernanp.simrac.model.ModuleModel;

public class RoleDTO implements java.io.Serializable {
	
	private int id;	
	
	private String name;	
	
	private List<ModuleModel> modules;
	
	private Date registrationDate;
	
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
}
