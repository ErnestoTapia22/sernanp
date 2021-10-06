package pe.sernanp.simrac.model;

import java.util.Date;
import java.util.Calendar;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (name = "t_acuerdoplan")
@AssociationOverrides({
	@AssociationOverride(name = "pk.stock", foreignKey = @ForeignKey(name="fk_acuerdoplan_pm"),  joinColumns = @JoinColumn(name = "int_planid")),
	@AssociationOverride(name = "pk.category", foreignKey = @ForeignKey(name="fk_acuerdoplan_ac"), joinColumns = @JoinColumn(name = "int_acuerdoid")) })
public class AgreementPlanModel  implements java.io.Serializable {	
	
	private AgreementPlanDTO pk = new AgreementPlanDTO();
	
	private Date registrationDate;
	
	private Boolean state;

	@EmbeddedId
	public AgreementPlanDTO getPk() {
		return pk;
	}

	public void setPk(AgreementPlanDTO masterPlan) {
		this.pk = masterPlan;
	}

	@Transient
	public MasterPlanModel getStock() {
		return getPk().getStock();
	}

	public void setStock(MasterPlanModel stock) {
		getPk().setStock(stock);
	}

	@Transient
	public ConservationAgreementModel getCategory() {
		return getPk().getCategory();
	}

	public void setCategory(ConservationAgreementModel category) {
		getPk().setCategory(category);
	}
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	public Date getRegistrationDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	@Column (name= "bol_flg", nullable=false)	
	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}	
}