package pe.github.sernan.model;

import java.sql.Date;

	public class ConservationAgreement {

	private int id;
	private AgreementState stateid;
	private String typeecosystemid;
	private Date firm;
	private int validity;
	private Boolean state;
	private String category;
	private String name;
	private int vigency;
	private String code;
	
	
	public ConservationAgreement() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public AgreementState getStateid() {
		return stateid;
	}

	public void setStateid(AgreementState Stateid) {
		this.stateid = Stateid;
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
