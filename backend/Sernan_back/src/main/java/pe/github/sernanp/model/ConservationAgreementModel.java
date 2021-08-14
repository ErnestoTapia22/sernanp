package pe.github.sernanp.model;

import java.sql.Date;

public class ConservationAgreementModel extends BaseModel {

	private AgreementStateModel _agreementState;
	private String _typeecosystemid;
	private Date _firm;
	private int _validity;
	private String _category;	
	private int _vigency;
	
	public void setCategory(String category) {
		this._category = category;
	}

	public String getCategory() {
		return _category;
	}	
	
	public AgreementStateModel getAgreementState() {
		return _agreementState;
	}

	public void setAgreementState(AgreementStateModel Stateid) {
		this._agreementState = Stateid;
	}

	public String getTypeecosystemid() {
		return _typeecosystemid;
	}

	public void setTypeecosystemid(String Typeecosystemid) {
		this._typeecosystemid = Typeecosystemid;
	}

	public Date getFirm() {
		return _firm;
	}

	public void setFirm(Date firm) {
		this._firm = firm;
	}

	public int getValidity() {
		return _validity;
	}

	public void setValidity(int validity) {
		this._validity = validity;
	}
	
	public int getVigency() {
		return _vigency;
	}

	public void setVigency(int value) {
		this._vigency = value;
	}
}
