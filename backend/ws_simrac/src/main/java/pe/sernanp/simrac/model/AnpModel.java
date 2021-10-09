package pe.sernanp.simrac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table (name = "v_gdb_anp_plan")
public class AnpModel {	

	@Column (name= "anp_id")
	@Id
	private int id;
	
	@Column (name= "anp_nomb")
	private String name;
	
	@Column (name= "anp_cate")
	private String _category;
	
	@Column (name= "anp_ubpo")
	private String _district;
	
	@Column (name= "anp_codi")
	private String _code;
	
	@Column (name= "withmasterplan")
	private int _withMasterPlan;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
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
	
	public String getCode() {
		return _code;
	}
		
	public void setCode(String code) {
		_code = code;		
	}
	
	public int getWithMasterPlan() {
		return _withMasterPlan;
	}
		
	public void setWithMasterPlan(int withMasterPlan) {
		_withMasterPlan = withMasterPlan;
	}
	
	public String getFullName() {
		return _category + " " + name;
	}
}
