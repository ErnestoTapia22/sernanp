package pe.sernanp.simrac.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name = "t_objetivo", indexes = {@Index(name = "idx_objetivo", columnList = "srl_id",unique = true)})
public class ObjetiveModel {

	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="t_objetivo_srl_id_seq")
	@SequenceGenerator(name="t_objetivo_srl_id_seq", sequenceName="t_objetivo_srl_id_seq", allocationSize=1)
	private int id;
	
	@JoinColumn(name= "int_planmaestroid")
	@ManyToOne
	private MasterPlanModel masterPlan;
	
	@JoinColumn(name= "int_componenteid")
	@ManyToOne
	private ComponentModel component;
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;
	
	@Column (name= "var_cod", length = 50)
	private String code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MasterPlanModel getMasterPlan() {
		return masterPlan;
	}

	public void setMasterPlan(MasterPlanModel planMaster) {
		this.masterPlan = planMaster;
	}

	public ComponentModel getComponent() {
		return component;
	}

	public void setComponent(ComponentModel componente) {
		this.component = componente;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}	
}