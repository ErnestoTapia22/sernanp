package pe.github.sernan.model;

import java.sql.Date;

public class ConservationAgreement {

	private int id;
	private AgreementState agreementState;
	private String typeecosystemid;
	private Date firm;
	private int validity;
	private Boolean state;
	private String Category;
	private String Name;
	private String Code;

	public ConservationAgreement() {

	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AgreementState getAgreementState() {
		return agreementState;
	}

	public void setAgreementState(AgreementState Stateid) {
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

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

}
