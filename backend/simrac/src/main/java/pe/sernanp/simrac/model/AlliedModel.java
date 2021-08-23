package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class AlliedModel extends BaseModel {
	
	private AlliedCategoryModel _alliedcategory;
	private ConservationAgreementModel _agreement;
	
	
	public AlliedCategoryModel getAlliedCategory() {
		return _alliedcategory;
	}
	
	public void setAlliedCategory (AlliedCategoryModel alliedcategoryid) {
		_alliedcategory = alliedcategoryid;
	}

	public ConservationAgreementModel getConservationAgreement() {
		return _agreement;
	}
	
	public void setConservationAgreement (ConservationAgreementModel conservationagreement) {
		_agreement = conservationagreement;
	}
	
}