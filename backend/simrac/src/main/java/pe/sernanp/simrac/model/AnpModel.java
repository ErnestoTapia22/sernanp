package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class AnpModel extends BaseModel {

	private String _category;
	private String _district;
	
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
	
}
