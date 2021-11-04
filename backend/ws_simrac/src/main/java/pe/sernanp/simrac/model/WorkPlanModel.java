package pe.sernanp.simrac.model;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name = "t_plan_trabajo", indexes = {@Index(name = "idx_plantrabajo", columnList = "srl_id",unique = true)})
public class WorkPlanModel {

	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="t_plan_trabajo_srl_id_seq")
	@SequenceGenerator(name="t_plan_trabajo_srl_id_seq", sequenceName="t_plan_trabajo_srl_id_seq", allocationSize=1)
	private int id;
	
	@JoinColumn (name= "int_acuerdoid", referencedColumnName = "srl_id", nullable=false, foreignKey = @ForeignKey(name="fk_plantrabajo_acuerdo"))
	@ManyToOne
	private ConservationAgreementModel ConservationAgreement;
	
	@Column (name= "var_nom", length=200, nullable=false)
	private String name;
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;
	
	@Column (name= "int_anio", columnDefinition="INTEGER")
	private int year;
	
	@Column (name= "int_version", columnDefinition="INTEGER")
	private int version;
	
	@Column (name= "bol_activo", nullable=false)	
	private Boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ConservationAgreementModel getConservationAgreement() {
		return ConservationAgreement;
	}

	public void setConservationAgreement(ConservationAgreementModel conservationAgreement) {
		ConservationAgreement = conservationAgreement;
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