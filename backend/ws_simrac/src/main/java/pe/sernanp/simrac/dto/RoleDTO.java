package pe.sernanp.simrac.dto;

import java.util.Date;
import java.util.List;
import pe.sernanp.simrac.model.ModuleModel;

public class RoleDTO {
	
	private int id;	
	
	private List<ModuleModel> modules;
	
	private Date registrationDate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
