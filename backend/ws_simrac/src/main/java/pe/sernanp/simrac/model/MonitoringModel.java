package pe.sernanp.simrac.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import pe.sernanp.simrac.dto.ActivityDTO;

@Entity
@Table (name = "t_monitoreo", indexes = {@Index(name = "idx_monitoreo", columnList = "srl_id",unique = true)})
public class MonitoringModel {
	
	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="t_monitoreo_srl_id_seq")
	@SequenceGenerator(name="t_monitoreo_srl_id_seq", sequenceName="t_monitoreo_srl_id_seq", allocationSize=1)
	private int id;
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;
	
	@Column (name= "txt_com", columnDefinition="TEXT")
	private String comment;
	
	@Column (name= "txt_logro", columnDefinition="TEXT")
	private String achievement;
	
	@Column (name= "txt_recomendacion", columnDefinition="TEXT")
	private String recomendation;
	
	@Column (name= "bol_activo", nullable=false)	
	private Boolean active;
	
	@Column (name= "txt_evaluacion", columnDefinition="TEXT")
	private String evaluation;
	
	@Transient
	private List<ActivityDTO> activities;

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

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
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
	
	public List<ActivityDTO> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivityDTO> activities) {
		this.activities = activities;
	}
}