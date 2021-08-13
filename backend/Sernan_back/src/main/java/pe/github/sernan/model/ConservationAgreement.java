package pe.github.sernan.model;

import java.sql.Date;

	public class ConservationAgreement {

	private int id;
	private int stateid;
	private String typeecosystemid;
	private Date firm;
	private int validity;
	private Boolean state;
	
	public ConservationAgreement() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getStateid() {
		return stateid;
	}

	public void setStateid(int Stateid) {
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
