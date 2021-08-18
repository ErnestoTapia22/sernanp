package pe.sernanp.simrac.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class ConservationAgreementModel extends BaseGeometryEntity {

	private AgreementStateModel _agreementState;
	private Date _firm;
	private int _validity;
	private int _vigency;
		
	public AgreementStateModel getAgreementState() {
		return _agreementState;
	}

	public void setAgreementState(AgreementStateModel Stateid) {
		this._agreementState = Stateid;
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
