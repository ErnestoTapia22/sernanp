package pe.sernanp.simrac.model;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "t_compromiso_externo", indexes = {@Index(name = "idx_compromisoexterno", columnList = "srl_id",unique = true)})
public class ExternalCommimentModel {

	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@JoinColumn (name= "int_acuerdoid", referencedColumnName = "srl_id", nullable=true)
	@ManyToOne
	private ConservationAgreementModel conservationAgreement;
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;
	
	@Column (name= "txt_objetivo", columnDefinition="TEXT")
	private String objetive;
	
	@Column (name= "txt_lineaaccion", columnDefinition="TEXT")
	private String actionLine;	
	
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
	public String getObjetive() {
		return objetive;
	}
	public void setObjetive(String objetive) {
		this.objetive = objetive;
	}
	public String getActionLine() {
		return actionLine;
	}
	public void setActionLine(String actionline) {
		this.actionLine = actionline;
	}
	public ConservationAgreementModel getConservationAgreement() {
		return conservationAgreement;
	}
	public void setConservationAgreement(ConservationAgreementModel conservationAgreement) {
		this.conservationAgreement = conservationAgreement;
	}
	
}