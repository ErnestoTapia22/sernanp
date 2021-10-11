package pe.sernanp.simrac.dto;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import pe.sernanp.simrac.model.AnswerModel;

public class MonitoringDTO {

	private int id;
	private String description;
	private Date registrationDate;
	private boolean state;
	private String comment;
	private String achievement;
	private String recomendation;
	private Boolean active;
	private String evaluation;
	private List<AnswerModel> activities;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	public String getRecomendation() {
		return recomendation;
	}

	public void setRecomendation(String recomendation) {
		this.recomendation = recomendation;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public List<AnswerModel> getActivities() {
		return activities;
	}

	public void setActivities(List<AnswerModel> activities) {
		this.activities = activities;
	}	
	
}