package pe.sernanp.simrac.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import pe.sernanp.simrac.model.ModuleModel;

public class UserDTO {
	
	private int id;
	
	private String name;
	
	private String userName;

	private String lastName;
	
	private List<ModuleModel> modules;	
	
	private int system;
	
	private String token;
	
	private Date registrationDate;	

	private RoleDTO role;
	
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
	
	public Date getRegistrationDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
