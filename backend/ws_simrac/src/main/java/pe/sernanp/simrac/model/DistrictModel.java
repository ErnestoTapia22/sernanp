package pe.sernanp.simrac.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table (name = "ubigeo", schema="sernanp")
public class DistrictModel implements Serializable {

	@Column (name= "idubigeo")
	@Id
	private Integer id;
	
	@Column (name= "coddpto")
	private String departmentId;
	
	@Column (name= "codprov")
	private String provinceId;
	
	@Column (name= "coddist")
	private String districtId;
	
	@Column (name= "nombre")
	private String name;

	public DistrictModel() {
		
	}
	
	public DistrictModel( Integer id, String departmentId, String provinceId, String districtId, String name) {
		this.id = id;
		this.departmentId = departmentId;
		this.provinceId = provinceId;
		this.districtId = districtId;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
}
