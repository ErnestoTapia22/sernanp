package pe.github.sernanp.model;

import java.sql.Date;

public class ConservationAgreementModel extends BaseModel {

	private int id;
	private AgreementStateModel agreementState;
	private String typeecosystemid;
	private Date firm;
	private int validity;
	private Boolean state;
	private String category;
	private String name;
	private int vigency;
	private String code;
	
	private String Category;
	private String Name;
	private String Code;


	public void setCategory(String category) {
		this.Category = category;
	}

	public String getCategory() {
		return Category;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		this.Code = code;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AgreementStateModel getAgreementState() {
		return agreementState;
	}

	public void setAgreementState(AgreementStateModel Stateid) {
		this.agreementState = Stateid;
	}

	public String getTypeecosystemid() {
		return typeecosystemid;
	}

	public void setTypeecosystemid(String Typeecosystemid) {
		this.typeecosystemid = Typeecosystemid;
	}

	public Date getFirm() {
		return firm;
	}

	public void setFirm(Date firm) {
		this.firm = firm;
	}

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
