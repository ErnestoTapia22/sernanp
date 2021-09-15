package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class DistrictModel extends BaseModel {

	private String _department;
	private String _province;
	
	public String getDepartment() {
		return _department;
	}
		
	public void setDepartment(String department) {
		_department = department;		
	}
	
	public String getProvince() {
		return _province;
	}
		
	public void setProvince(String province) {
		_province = province;		
	}
	
}
