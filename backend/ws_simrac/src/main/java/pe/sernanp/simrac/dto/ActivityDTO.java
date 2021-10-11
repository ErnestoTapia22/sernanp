package pe.sernanp.simrac.dto;
import java.util.Date;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.WorkPlanModel;


public class ActivityDTO {	
	
	private int id;	
	private String name;	
	private CommitmentModel commitment;	
	private WorkPlanModel workPlan;			
	private String description;	
	private Date registrationDate;	
	private Boolean state;	
	private int goal;	
	private Boolean active;		
	private String semester;
	private int value;

		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public CommitmentModel getCommitment() {
		return commitment;
	}
	public void setCommitment(CommitmentModel commitment) {
		this.commitment = commitment;
	}
	
	public WorkPlanModel getWorkPlan() {
		return workPlan;
	}
	public void setWorkPlan(WorkPlanModel workPlan) {
		this.workPlan = workPlan;
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
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}	
	
	public int getGoal() {
		return goal;
	}
	public void setGoal(int meta) {
		this.goal = meta;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}