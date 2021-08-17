package pe.sernanp.simrac.model;

import java.util.Date;

public class ConservationAgreementModel extends BaseModel {

	private AgreementStateModel _agreementState;
	private String _typeecosystemid;
	private Date _firm;
	private int _validity;
	private int _vigency;
		
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
