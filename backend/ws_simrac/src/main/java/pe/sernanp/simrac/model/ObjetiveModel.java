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
@Table (name = "t_objetivo", indexes = {@Index(name = "idx_objetivo", columnList = "srl_id",unique = true)})
public class ObjetiveModel {

	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	//@Column (name= "int_planmaestroid", columnDefinition="INTEGER")
	//private MasterPlanModel planMaster ;
	
	//@Column (name= "int_componentid", columnDefinition="INTEGER")
	//private ComponentModel componente;
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;
	
	@Column (name= "var_cod", length = 50, columnDefinition="CHARACTER VARYING")
	private String code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/* public MasterPlanModel getPlanMaster() {
		return planMaster;
	}

	public void setPlanMaster(MasterPlanModel planMaster) {
		this.planMaster = planMaster;
	}

	public ComponentModel getComponente() {
		return componente;
	}

	public void setComponente(ComponentModel componente) {
		this.componente = componente;
	} */

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}	
}