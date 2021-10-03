package pe.sernanp.simrac.model;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table (name = "t_plan_trabajo", indexes = {@Index(name = "idx_plantrabajo", columnList = "srl_id",unique = true)})
public class WorkPlanModel {

	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/*@Column (name= "int_acuerdoid", columnDefinition="INTEGER")
	private ConservationAgreementModel conservationAgreement;*/
	
	@Column (name= "var_nom", length=50, unique=true, nullable=false)
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

	/*public ConservationAgreementModel getConservationAgreement() {
		return conservationAgreement;
	}

	public void setConservationAgreement(ConservationAgreementModel conservationAgreement) {
		this.conservationAgreement = conservationAgreement;
	}*/

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
		return registrationDate;
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