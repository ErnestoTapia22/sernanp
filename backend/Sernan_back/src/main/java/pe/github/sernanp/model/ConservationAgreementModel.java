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
	
}
