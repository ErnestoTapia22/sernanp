package pe.sernanp.simrac.dto;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import pe.sernanp.simrac.model.ActivityModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;

public class WorkPlanDTO {

	private int id;
	private ConservationAgreementModel conservationAgreement;
	private String name;
	private String description;
	private Date registrationDate;
	private boolean state;
	private int year;
	private int version;
	private Boolean active;
	private List<ActivityModel> activities;
		
	public List<ActivityModel> getActivities() {
		return activities;
	}
	public void setActivities(List<ActivityModel> activities) {
		this.activities = activities;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ConservationAgreementModel getConservationAgreement() {
		return conservationAgreement;
	}
	public void setConservationAgreement(ConservationAgreementModel conservationAgreement) {
		this.conservationAgreement = conservationAgreement;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getRegistrationDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
		
}