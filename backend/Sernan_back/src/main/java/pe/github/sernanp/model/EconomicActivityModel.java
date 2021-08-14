package pe.github.sernanp.model;


public class EconomicActivityModel extends BaseModel {
	
	private int id;
	private String name;
	private String description;
	private java.sql.Timestamp registratiodate;
	private Boolean state;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String Name) {
		this.name = Name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String Description) {
		this.description = Description;
	}
	
	public java.sql.Timestamp getRegistratioDate() {
		return registratiodate;
	}

	public void setRegistratioDate(java.sql.Timestamp RegistratioDate) {
		this.registratiodate = RegistratioDate;
	}

	public void setState(boolean state) {
		this.state = state;
	}	
	
}
