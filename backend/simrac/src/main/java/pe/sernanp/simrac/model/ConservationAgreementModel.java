package pe.sernanp.simrac.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;

@Entity
public class ConservationAgreementModel extends BaseModel {

	private AgreementStateModel _agreementState;
	private int _vigency;
	private Date _firm;
	private int _partmen;
	private int _partwomen;
	private String _benperson;
	private String _benindirect;
	private int _numfamily;
	private String _benfamily;
	private double _areaambitc;
	private double _producedarea;
	private String _detalleproduction;
	
		
	public AgreementStateModel getAgreementState() {
		return _agreementState;
	}

	public void setAgreementState(AgreementStateModel Stateid) {
		this._agreementState = Stateid;
	}

	public int getVigency () {
		return _vigency;
	}
	
	public void setVigency (int vigency) {
		_vigency = vigency;
	}
	
	public Date getFirm () {
		return _firm;
	}
	
	public void setFirm (Date firm) {
		_firm = firm;
	}
	
	public int getPartMen () {
		return _partmen;
	}
	
	public void setPartMen (int partmen) {
		_partmen = partmen;
	}
	
	public int getPartWomen () {
		return _partwomen;
	}
	
	public void setPartWomen (int partwomen) {
		_partwomen = partwomen;
	}
	
	public String getBenPerson () {
		return _benperson;
	}
	
	public void setBenPerson (String benperson) {
		_benperson = benperson;
	}
	
	public String getBenIndirect () {
		return _benindirect;
	}
	public void setBenIndirect (String benindirect) {
		_benindirect = benindirect;
	}
	
	public int getNumFamily () {
		return _numfamily;
	}
		
	public void setNumFamily (int numfamily) {
		_numfamily = numfamily;
	}
	
	public String getBenFamily () {
		return _benfamily;
	}
	public void setBenFamily (String benfamily) {
		_benfamily = benfamily;
	}
	
	public Double getAreaAmbitc () {
		return _areaambitc;
	}
	
	public void setAreaAmbitc (Double areaambitc) {
		_areaambitc = areaambitc;
	}
	
	public Double getProducedArea () {
		return _producedarea;
	}
	public void setProducedArea (Double producedarea) {
		_producedarea = producedarea;
	}
	
	public String getDetalleProduction () {
		return _detalleproduction;
	}
	public void setDetalleProduction (String detalleproduction) {
		_detalleproduction = detalleproduction;
	}
}
