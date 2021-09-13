package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class AnpModel extends BaseModel {

	private String _category;
	private String _district;
	private int _withMasterPlan;
	
	public String getCategory() {
		return _category;
	}
		
	public void setCategory(String category) {
		_category = category;		
	}
	
	public String getDistrict() {
		return _district;
	}
		
	public void setDistrict(String district) {
		_district = district;
	}
	
	public int getwithMasterPlan() {
		return _withMasterPlan;
	}
		
	public void setWithMasterPlan(int withMasterPlan) {
		_withMasterPlan = withMasterPlan;
	}
	
}
