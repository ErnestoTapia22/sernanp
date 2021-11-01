package pe.sernanp.simrac.dto;

import java.util.Date;
import java.util.List;
import java.util.Calendar;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import pe.sernanp.extension.CustomDoubleSerializer;
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
	
	private Double progress = 0.0;
	
	private String indicator;
	private List<CommitmentModel> commitments;	
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
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	
	@JsonFormat(pattern=".##")
	public double getProgress() {		
		if (this.goal != 0 && this.value != 0)
			return (this.value * 100.00 ) / this.goal;
		else 
			return 0;
	}
}