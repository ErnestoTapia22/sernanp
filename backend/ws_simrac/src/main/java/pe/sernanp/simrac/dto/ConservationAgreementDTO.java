package pe.sernanp.simrac.dto;

import java.util.Date;

import pe.sernanp.simrac.model.AgreementStateModel;

public class ConservationAgreementDTO implements java.io.Serializable {
	
	private String code;
	
	private String name;
	
	private AnpDTO anp;

	private AgreementStateModel agreementState;
	
	private String departmentId;
	
	private String provinceId;
	
	private String districtId;
	
	private Date firm;

	private Date firmEnd;
	
	private boolean state;

	private int vigency;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AnpDTO getAnp() {
		return anp;
	}

	public void setAnp(AnpDTO anp) {
		this.anp = anp;
	}

	public AgreementStateModel getAgreementState() {
		return agreementState;
	}

	public void setAgreementState(AgreementStateModel agreementState) {
		this.agreementState = agreementState;
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
	
	public Date getFirm() {
		return firm;
	}

	public void setFirm(Date firm) {
		this.firm = firm;
	}

	public Date getFirmEnd() {
		return firmEnd;
	}

	public void setFirmEnd(Date firmEnd) {
		this.firmEnd = firmEnd;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	public int getVigency() {
		return vigency;
	}
	
	public void setVigency(int vigency) {
		this.vigency = vigency;
	}
}
