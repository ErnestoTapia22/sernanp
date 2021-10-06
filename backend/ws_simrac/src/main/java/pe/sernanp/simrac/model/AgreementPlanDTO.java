package pe.sernanp.simrac.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class AgreementPlanDTO implements java.io.Serializable {

	private MasterPlanModel stock;
    private ConservationAgreementModel category;

	@ManyToOne
	public MasterPlanModel getStock() {
		return stock;
	}

	public void setStock(MasterPlanModel stock) {
		this.stock = stock;
	}

	@ManyToOne
	public ConservationAgreementModel getCategory() {
		return category;
	}

	public void setCategory(ConservationAgreementModel category) {
		this.category = category;
	}	
}
