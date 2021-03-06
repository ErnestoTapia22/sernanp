package pe.sernanp.simrac.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class ConservationAgreementModel extends BaseModel {
	
	private EcosystemTypeModel _ecosystemType;
	private AnpModel _anp;
	private AgreementStateModel _agreementState;
	private SourceModel _source;
	private String _districtId;
	private String _departmentId;
	private String _provinceId;
	private String _districtName;
	
	private int _vigency;
	private Date _firm;
	private Date _firmEnd;
	private int _partmen;
	private int _partwomen;
	private String _benperson;
	private String _benindirect;
	private Double _numfamily;
	private String _benfamily;
	private double _areaambitc;
	private double _producedarea;
	private String _detailProduction;
	
	private double _resthect;
	private String _restdet;
	private String _sectnom;
	private double _secthect;
	private String _sectdet;
	private String _territorymod;
	private boolean _finanapa;
	private double _finannum;
	private String _comment;
	private String _genobj;
	private String _finanmod;
	private String _fondname;
	private boolean _allied;
	
	
	
	public EcosystemTypeModel getEcosystemType () {
		return _ecosystemType;
	}
	
	public void setEcosystemType (EcosystemTypeModel ecosystemType) {
		_ecosystemType = ecosystemType;
	}
	
	
	public AnpModel getAnp() {
		return _anp;
	}

	public void setAnp(AnpModel anpid) {
		_anp = anpid;
	}
	
	public AgreementStateModel getAgreementState() {
		return _agreementState;
	}

	public void setAgreementState(AgreementStateModel agreementState) {
		this._agreementState = agreementState;
	}

	public String getDistrictId () {
		return _districtId;
	}
	
	public void setDistrictId (String districtid) {
		_districtId = districtid;
	}
	
	public String getDistrictName () {
		return _districtName;
	}
	
	public void setDistrictName (String districtname) {
		_districtName = districtname;
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
	
	public Double getNumFamily () {
		return _numfamily;
	}
		
	public void setNumFamily (Double numfamily) {
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
	
	public String getDetailProduction () {
		return _detailProduction;
	}
	
	public void setDetailProduction (String detailproduction) {
		_detailProduction = detailproduction;
	}
	
	public SourceModel getSource() {
		return _source;
	}

	public void setSource(SourceModel source) {
		this._source = source;
	}
	
	public Double getRestHect () {
		return _resthect;
	}
	
	public void setRestHect (Double resthect) {
		_resthect = resthect;
	}
	
	public String getRestdet () {
		return _restdet;
	}
	
	public void setRestdet (String restdet) {
		_restdet = restdet;
	}
	
	public String getSectNom () {
		return _sectnom;
	}
	
	public void setSectNom (String sectnom) {
		_sectnom = sectnom ;
	}
	
	public Double getSectHect () {
		return _secthect;
	}
	
	public void setSectHect (Double secthect) {
		_secthect = secthect;
	}
	
	public String getSectDet () {
		return _sectdet;
	}
	
	public void setSectDet (String sectdet) {
		_sectdet = sectdet ;
	}
		
	public String getTerritoryMod () {
		return _territorymod;
	}
	
	public void setTerritoryMod (String territorymod) {
		_territorymod = territorymod ;
	}
	
	public Boolean getFinanApa () {
		return _finanapa;
	}
	
	public void setFinanApa (Boolean finanapa) {
		_finanapa = finanapa ;
	}
	
	public Double getFinanNum () {
		return _finannum;
	}
	
	public void setFinanNum (Double finannum) {
		_finannum = finannum;
	}	
	
	public String getComment () {
		return _comment;
	}
	
	public void setComment (String comment) {
		_comment = comment ;
	}
	
	public String getGenObj () {
		return _genobj;
	}
	
	public void setGenObj  (String genobj) {
		_genobj = genobj ;
	}
	
	public String getFinanMod () {
		return _finanmod;
	}
	
	public void setFinanMod (String finanmod) {
		_finanmod = finanmod ;
	}
	
	public String getFondName () {
		return _fondname;
	}
	
	public void setFondName (String fondname) {
		_fondname = fondname;
	}
	
	public Boolean getAllied () {
		return _allied;
	}
	
	public void setAllied (Boolean allied) {
		_allied = allied ;
	}		
	
	public String getDepartmentId () {
		return _departmentId;
	}
	
	public void setDepartmentId(String departmentId) {
		_departmentId = departmentId;
	}
	
	public String getProvinceId () {
		return _provinceId;
	}
	
	public void setProvinceId(String provinceId) {
		_provinceId = provinceId;
	}
	
	public Date getFirmEnd () {
		return _firmEnd;
	}
	
	public void setFirmEnd (Date firmEnd) {
		_firmEnd = firmEnd;
	}
}
