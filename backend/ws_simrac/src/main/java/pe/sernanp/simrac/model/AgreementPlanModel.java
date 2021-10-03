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
@Table (name = "t_acuerdoplan")
public class AgreementPlanModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/* @Column (name= "int_planid")
	private MasterPlanModel masterPlan;
	
	@Column (name= "int_acuerdoid")
	private ConservationAgreementModel agreement; */
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;

	/* public MasterPlanModel getMasterPlan() {
		return masterPlan;
	}

	public void setMasterPlan(MasterPlanModel masterPlan) {
		this.masterPlan = masterPlan;
	}

	public ConservationAgreementModel getAgreement() {
		return agreement;
	}

	public void setAgreement(ConservationAgreementModel agreement) {
		this.agreement = agreement; 
	}*/

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
	
}