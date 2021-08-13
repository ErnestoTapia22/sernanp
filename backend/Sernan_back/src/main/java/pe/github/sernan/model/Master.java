package pe.github.sernan.model;
import java.sql.Date;

public class Master {

	private int Id;
	
	private String Name;
	private String LastName;
	private String Email;
	private int Age;
	private Date Registration;
	private Boolean State;
	private String Description;
	
	
	
	
	public Master() {
		
	}
	public Date getRegistration() {
		return Registration;
	}
	public void setRegistration(Date registration) {
		Registration = registration;
	}
	public Boolean getState() {
		return State;
	}
	public void setState(Boolean state) {
		State = state;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}
	
}
