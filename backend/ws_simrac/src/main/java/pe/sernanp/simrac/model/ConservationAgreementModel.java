package pe.sernanp.simrac.model;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
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
	
	@JoinColumn (name= "int_anpid", nullable=true, foreignKey = @ForeignKey(javax.persistence.ConstraintMode.NO_CONSTRAINT))
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
	private String benPerson;
	
	@Column (name= "int_num_familia")	
	private int numfamily;
	
	@Column (name= "txt_det_ben_fam", columnDefinition="TEXT")
	private String benfamily;
	
	@Column (name= "txt_ben_ind", columnDefinition="TEXT")
	private String benindirect;
	
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
	private double surfaceAmbito;
	
	@Column (name= "txt_superfice_intervencion", columnDefinition="TEXT")
	private String surfaceIntervention;
	
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
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
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
	public int getPartMen() {
		return partmen;
	}
	public void setPartMen(int partmen) {
		this.partmen = partmen;
	}
	public int getPartWomen() {
		return partwomen;
	}
	public void setPartWomen(int partwomen) {
		this.partwomen = partwomen;
	}
	public String getBenPerson() {
		return benPerson;
	}
	public void setBenPerson(String beneficyind) {
		this.benPerson = beneficyind;
	}
	public String getBenIndirect() {
		return benindirect;
	}
	public void setBenIndirect(String benindirect) {
		this.benindirect = benindirect;
	}
	public int getNumFamily() {
		return numfamily;
	}
	public void setNumFamily(int numfamily) {
		this.numfamily = numfamily;
	}
	public String getBenFamily() {
		return benfamily;
	}
	public void setBenFamily(String benfamily) {
		this.benfamily = benfamily;
	}
	public double getAreaAmbitc() {
		return areaambitc;
	}
	public void setAreaAmbitc(double areaambitc) {
		this.areaambitc = areaambitc;
	}
	public double getProducedArea() {
		return producedarea;
	}
	public void setProducedArea(double producedarea) {
		this.producedarea = producedarea;
	}
	public String getDetailProduction() {
		return detailProduction;
	}
	public void setDetailProduction(String detailProduction) {
		this.detailProduction = detailProduction;
	}
	public double getRestHect() {
		return resthect;
	}
	public void setRestHect(double resthect) {
		this.resthect = resthect;
	}
	public String getRestdet() {
		return restdet;
	}
	public void setRestdet(String restdet) {
		this.restdet = restdet;
	}
	public String getSectNom() {
		return sectnom;
	}
	public void setSectNom(String sectnom) {
		this.sectnom = sectnom;
	}
	public double getSectHect() {
		return secthect;
	}
	public void setSectHect(double secthect) {
		this.secthect = secthect;
	}
	public String getSectDet() {
		return sectdet;
	}
	public void setSectDet(String sectdet) {
		this.sectdet = sectdet;
	}
	public String getTerritoryMod() {
		return territorymod;
	}
	public void setTerritoryMod(String territorymod) {
		this.territorymod = territorymod;
	}
	public boolean isFinanApa() {
		return finanapa;
	}
	public void setFinanApa(boolean finanapa) {
		this.finanapa = finanapa;
	}
	public double getFinanNum() {
		return finannum;
	}
	public void setFinanNum(double finannum) {
		this.finannum = finannum;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getGenObj() {
		return genobj;
	}
	public void setGenObj(String genobj) {
		this.genobj = genobj;
	}
	public String getFinanMod() {
		return finanmod;
	}
	public void setFinanMod(String finanmod) {
		this.finanmod = finanmod;
	}
	public String getFondName() {
		return fondname;
	}
	public void setFondName(String fondname) {
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
	public double getSurfaceAmbito() {
		return surfaceAmbito;
	}
	public void setSurfaceAmbito(double surface) {
		this.surfaceAmbito = surface;
	}
	public String getSurfaceIntervention() {
		return surfaceIntervention;
	}
	public void setSurfaceIntervention(String surfaceIntervention) {
		this.surfaceIntervention = surfaceIntervention;
	}
}