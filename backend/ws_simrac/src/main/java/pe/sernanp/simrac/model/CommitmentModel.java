package pe.sernanp.simrac.model;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import pe.sernanp.extension.CustomDoubleSerializer;

@Entity
@Table (name = "t_compromiso", indexes = {@Index(name = "idx_compromiso", columnList = "srl_id",unique = true)})
public class CommitmentModel implements java.io.Serializable {

	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="t_compromiso_srl_id_seq")
	@SequenceGenerator(name="t_compromiso_srl_id_seq", sequenceName="t_compromiso_srl_id_seq", allocationSize=1)
	private int id;
	
	@JoinColumn (name= "int_acuerdoid", referencedColumnName = "srl_id", nullable=false, foreignKey = @ForeignKey(name="fk_compromiso_acuerdo"))
	@ManyToOne
	private ConservationAgreementModel ConservationAgreement;
	
	@JoinColumn (name= "int_aliadoid", referencedColumnName = "srl_id", nullable=false, foreignKey = @ForeignKey(name="fk_compromiso_aliado"))
	@ManyToOne
	private AlliedModel allied;
	
	@JoinColumn (name= "int_lineaacionid", referencedColumnName = "srl_id", nullable=false, foreignKey = @ForeignKey(name="fk_compromiso_la"))
	@ManyToOne
	private ActionLineModel actionLine;
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;
	
	@Column (name= "var_indicador", columnDefinition="TEXT", nullable=true)
	private String indicator;
	
	@Column (name= "bol_activo", nullable=false)	
	private Boolean active;
	
	@Transient	
	private Double progress = 0.0;
	
	@Transient	
	private int rowspan;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ConservationAgreementModel getConservationAgreement() {
		return ConservationAgreement;
	}

	public void setConservationAgreement(ConservationAgreementModel conservationAgreement) {
		ConservationAgreement = conservationAgreement;
	} 

	public AlliedModel getAllied() {
		return allied;
	}

	public void setAllied(AlliedModel allied) {
		this.allied = allied;
	}

	public ActionLineModel getActionLine() {
		return actionLine;
	}

	public void setActionLine(ActionLineModel actionLine) {
		this.actionLine = actionLine;
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

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String _indicator) {
		this.indicator = _indicator;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	@JsonFormat(pattern=".##")
	public double getProgress () {
		return progress;
	}
	
	public void setProgress (int value, int goal) {
		progress = ((value * 100.00 ) / goal);
	}
	
	public void setProgress (double value) {
		progress = value;
	}
	
	public int getRowspan () {
		return rowspan;
	}
	
	public void setRowspan (int value) {
		rowspan = value;
	}
	
	public CommitmentModel(int id) {
		this.id = id;
	}
	
	public CommitmentModel() {		
	}
}