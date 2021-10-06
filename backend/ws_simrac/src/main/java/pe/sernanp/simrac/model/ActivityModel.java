package pe.sernanp.simrac.model;

import java.util.Date;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table (name = "t_actividad", indexes = {@Index(name = "idx_actividad", columnList = "srl_id",unique = true)})
public class ActivityModel {
	
	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	//@Column (name= "int_compromisoid", columnDefinition="INTEGER")
	//private CommitmentModel commiment;
	
	//@Column (name= "int_plantrabajoid", columnDefinition="INTEGER")
	//private WorkPlanModel workPlan;
	
	@Column (name= "var_nom", length=50, unique=true, nullable=false)
	private String name;
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;	
		
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
}