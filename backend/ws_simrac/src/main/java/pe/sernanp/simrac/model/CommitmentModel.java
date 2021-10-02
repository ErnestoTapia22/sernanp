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
@Table (name = "t_compromiso", indexes = {@Index(name = "idx_compromiso", columnList = "srl_id",unique = true)})
public class CommitmentModel {

	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/*@Column (name= "int_acuerdoid", columnDefinition="INTEGER")
	private ConservationAgreementModel ConservationAgreement;
	
	@Column (name= "int_aliadoid", columnDefinition="INTEGER")
	private AlliedModel allied;
	
	@Column (name= "int_lineaacionid", columnDefinition="INTEGER")
	private ActionLineModel actionLine;*/
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;
	
	@Column (name= "var_indicador", length=50, columnDefinition= "CHARACTER VARYING")
	private String name;
	
	@Column (name= "bol_activo", nullable=false)	
	private Boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*public ConservationAgreementModel getConservationAgreement() {
		return ConservationAgreement;
	}

	public void setConservationAgreement(ConservationAgreementModel conservationAgreement) {
		ConservationAgreement = conservationAgreement;
	} 

	public AlliedModel getAllied() {
		return allied;
	}

	public void setAllied(AlliedModel allied) {
		this.allied = allied;
	}

	public ActionLineModel getActionLine() {
		return actionLine;
	}

	public void setActionLine(ActionLineModel actionLine) {
		this.actionLine = actionLine;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}		
	
}