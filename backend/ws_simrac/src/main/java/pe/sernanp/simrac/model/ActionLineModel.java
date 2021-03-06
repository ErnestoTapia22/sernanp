package pe.sernanp.simrac.model;

import java.util.Date;
import java.util.Calendar;
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
import javax.persistence.Transient;

@Entity
@Table (name = "t_linea_accion", indexes = {@Index(name = "idx_lineaaccion", columnList = "srl_id",unique = true)})
public class ActionLineModel {

	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="t_linea_accion_srl_id_seq")
	@SequenceGenerator(name="t_linea_accion_srl_id_seq", sequenceName="t_linea_accion_srl_id_seq", allocationSize=1)
	private int id;
	
	@JoinColumn(name= "int_objetivoid", referencedColumnName = "srl_id", nullable=false, foreignKey = @ForeignKey(name="fk_lineaaccion_objetivo"))
	@ManyToOne
	private ObjetiveModel _objetive;
	
	@Column (name= "var_nom", columnDefinition="TEXT", nullable=false)
	private String name;
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;

	@Transient	
	private int rowspan;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ObjetiveModel getObjetive() {
		return _objetive;
	}

	public void setObjetive(ObjetiveModel objetive) {
		this._objetive = objetive;
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
	
	public int getRowspan () {
		return rowspan;
	}
	
	public void setRowspan (int value) {
		rowspan = value;
	}
}