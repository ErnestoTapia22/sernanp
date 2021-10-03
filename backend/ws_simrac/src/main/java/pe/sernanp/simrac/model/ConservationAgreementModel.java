package pe.sernanp.simrac.model;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "t_acuerdo_conservacion", indexes = {@Index(name = "idx_acuerdoconservacion", columnList = "srl_id",unique = true)})
public class ConservationAgreementModel {
	
	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@JoinColumn (name= "int_estadoacuerdoid", referencedColumnName = "srl_id", nullable=true)
	@ManyToOne
	private AgreementStateModel _agreementState;
	
	@JoinColumn (name= "int_fuenteid", referencedColumnName = "srl_id", nullable=true)
	@ManyToOne
	private SourceModel source;
	
	@JoinColumn (name= "int_anpid", nullable=true)
	@ManyToOne
	private AnpModel anp;
	
	@Column (name= "var_distritoid", length=6)
	private String districtId;
	
	@Column (name= "var_nom", length=200, nullable=true)
	private String name;
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;	
	
	@Column (name= "int_vig")	
	private int vigency;
	
	@Column (name= "var_cod", length=20)	
	private String code;
	
	@Column (name= "dte_fec_firma", columnDefinition= "DATE", nullable=false)
	private Date firm;
	
	@Column (name= "int_part_hombres")	
	private int partmen;
	
	@Column (name= "int_part_mujeres")	
	private int  partwomen;
	
	@Column (name= "txt_det_ben_per", columnDefinition="TEXT")
	private String benindirect;
	
	@Column (name= "int_num_familia")	
	private int numfamily;
	
	@Column (name= "txt_det_ben_fam", columnDefinition="TEXT")
	private String benfamily;
	
	@Column (name= "txt_ben_ind", columnDefinition="TEXT")
	private String beneficyind;
	
	@Column (name= "num_hect_amb", columnDefinition="NUMERIC (12,4)")
	private double areaambitc;
	
	@Column (name= "num_hect_prod", columnDefinition="NUMERIC (12,4)")
	private double producedarea;
	
	@Column (name= "txt_det_prod", columnDefinition="TEXT")
	private String detailProduction;
	
	@Column (name= "num_hect_rest", columnDefinition="NUMERIC (12,4)")
	private double resthect;
	
	@Column (name= "txt_det_rest", columnDefinition="TEXT")
	private String restdet;
	
	@Column (name= "txt_nom_sect", columnDefinition="TEXT")
	private String sectnom;
	
	@Column (name= "num_hect_sect", columnDefinition="NUMERIC (12,4)")
	private double secthect;
	
	@Column (name= "txt_det_sect", columnDefinition="TEXT")
	private String sectdet;
	
	@Column (name= "txt_mod_territorio", columnDefinition="TEXT")
	private String territorymod;
	
	@Column (name= "bol_apa_finan")
	private boolean finanapa;
	
	@Column (name= "num_finan", columnDefinition="NUMERIC (12,4)")
	private double finannum;
	
	@Column (name= "txt_com", columnDefinition="TEXT")
	private String comment;
	
	@Column (name= "txt_obj_gen", columnDefinition="TEXT")
	private String genobj;
	
	@Column (name= "txt_mod_finan", columnDefinition="TEXT")
	private String finanmod;
	
	@Column (name= "txt_nom_fondo", columnDefinition="TEXT")
	private String fondname;
	
	@Column (name= "bol_aliado")
	private boolean allied;
	
	@Column (name= "txt_localizacion", columnDefinition="TEXT")
	private String localization;
	
	@Column (name= "num_superficie_a", columnDefinition="NUMERIC (12,4)")
	private double surface;
	
		
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public AgreementStateModel getAgreementState() {
		return _agreementState;
	}
	public void setAgreementState(AgreementStateModel _agreementState) {
		this._agreementState = _agreementState;
	}
	public SourceModel getSource() {
		return source;
	}
	public void setSource(SourceModel _source) {
		this.source = _source;
	}
	public AnpModel getAnp() {
		return anp;
	}
	public void setAnp(AnpModel _anp) {
		this.anp = _anp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String distritid) {
		this.districtId = distritid;
	}
	public int getVigency() {
		return vigency;
	}
	public void setVigency(int vigency) {
		this.vigency = vigency;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getFirm() {
		return firm;
	}
	public void setFirm(Date firmEnd) {
		this.firm= firmEnd;
	}
	public int getPartmen() {
		return partmen;
	}
	public void setPartmen(int partmen) {
		this.partmen = partmen;
	}
	public int getPartwomen() {
		return partwomen;
	}
	public void setPartwomen(int partwomen) {
		this.partwomen = partwomen;
	}
	public String getBenindirect() {
		return benindirect;
	}
	public void setBenindirect(String benindirect) {
		this.benindirect = benindirect;
	}
	public int getNumfamily() {
		return numfamily;
	}
	public void setNumfamily(int numfamily) {
		this.numfamily = numfamily;
	}
	public String getBenfamily() {
		return benfamily;
	}
	public void setBenfamily(String benfamily) {
		this.benfamily = benfamily;
	}
	public String getBeneficyind() {
		return beneficyind;
	}
	public void setBeneficyind(String beneficyind) {
		this.beneficyind = beneficyind;
	}
	public double getAreaambitc() {
		return areaambitc;
	}
	public void setAreaambitc(double areaambitc) {
		this.areaambitc = areaambitc;
	}
	public double getProducedarea() {
		return producedarea;
	}
	public void setProducedarea(double producedarea) {
		this.producedarea = producedarea;
	}
	public String getDetailProduction() {
		return detailProduction;
	}
	public void setDetailProduction(String detailProduction) {
		this.detailProduction = detailProduction;
	}
	public double getResthect() {
		return resthect;
	}
	public void setResthect(double resthect) {
		this.resthect = resthect;
	}
	public String getRestdet() {
		return restdet;
	}
	public void setRestdet(String restdet) {
		this.restdet = restdet;
	}
	public String getSectnom() {
		return sectnom;
	}
	public void setSectnom(String sectnom) {
		this.sectnom = sectnom;
	}
	public double getSecthect() {
		return secthect;
	}
	public void setSecthect(double secthect) {
		this.secthect = secthect;
	}
	public String getSectdet() {
		return sectdet;
	}
	public void setSectdet(String sectdet) {
		this.sectdet = sectdet;
	}
	public String getTerritorymod() {
		return territorymod;
	}
	public void setTerritorymod(String territorymod) {
		this.territorymod = territorymod;
	}
	public boolean isFinanapa() {
		return finanapa;
	}
	public void setFinanapa(boolean finanapa) {
		this.finanapa = finanapa;
	}
	public double getFinannum() {
		return finannum;
	}
	public void setFinannum(double finannum) {
		this.finannum = finannum;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getGenobj() {
		return genobj;
	}
	public void setGenobj(String genobj) {
		this.genobj = genobj;
	}
	public String getFinanmod() {
		return finanmod;
	}
	public void setFinanmod(String finanmod) {
		this.finanmod = finanmod;
	}
	public String getFondname() {
		return fondname;
	}
	public void setFondname(String fondname) {
		this.fondname = fondname;
	}
	public boolean isAllied() {
		return allied;
	}
	public void setAllied(boolean allied) {
		this.allied = allied;
	}
	public String getLocalization() {
		return localization;
	}
	public void setLocalization(String location) {
		this.localization = location;
	}
	public double getSurface() {
		return surface;
	}
	public void setSurface(double surface) {
		this.surface = surface;
	}
	
}