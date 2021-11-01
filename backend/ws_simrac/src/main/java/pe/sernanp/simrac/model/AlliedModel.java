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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name = "t_aliado", indexes = {@Index(name = "idx_alido", columnList = "srl_id",unique = true)})
public class AlliedModel {
	
	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="t_aliado_srl_id_seq")
	@SequenceGenerator(name="t_aliado_srl_id_seq", sequenceName="t_aliado_srl_id_seq", allocationSize=1)
	private int id;
	
	@JoinColumn (name= "int_acuerdoid", referencedColumnName = "srl_id", nullable=true, foreignKey = @ForeignKey(name="fk_aliado_acuerdo"))
	@ManyToOne
	private ConservationAgreementModel conservationAgreement;
	
	@JoinColumn (name= "int_categoriaaliadoid", referencedColumnName = "srl_id", nullable=true, foreignKey = @ForeignKey(name="fk_aliado_categorialiado"))
	@ManyToOne
	private AlliedCategoryModel  alliedCategory;	
	
	@Column (name= "var_nom", length=200, nullable=false)
	private String name;
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;	
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public ConservationAgreementModel getConservationAgreement() {
		return conservationAgreement;
	}
	public void setConservationAgreemen(ConservationAgreementModel conservationAgreement) {
		this.conservationAgreement = conservationAgreement;
	}
	public AlliedCategoryModel getAlliedCategory() {
		return alliedCategory;
	}
	public void setAlliedCategory(AlliedCategoryModel alliedCategory) {
		this.alliedCategory = alliedCategory;
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
}