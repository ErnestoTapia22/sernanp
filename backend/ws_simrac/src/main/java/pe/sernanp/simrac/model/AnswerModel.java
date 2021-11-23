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
@Table (name = "t_respuesta", indexes = {@Index(name = "idx_respuesta", columnList = "srl_id",unique = true)})
public class AnswerModel {

	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="t_respuesta_srl_id_seq")
	@SequenceGenerator(name="t_respuesta_srl_id_seq", sequenceName="t_respuesta_srl_id_seq", allocationSize=1)
	private int id;
		
	@JoinColumn (name= "int_monitoreoid", referencedColumnName = "srl_id", nullable=false, foreignKey = @ForeignKey(name="fk_respuesta_monitoreo"))
	@ManyToOne
	private MonitoringModel monitoring;
	
	@JoinColumn (name= "int_actividadid", referencedColumnName = "srl_id", nullable=false, foreignKey = @ForeignKey(name="fk_respuesta_actividad"))
	@ManyToOne
	private ActivityModel activity;
	
	@Column (name= "num_valor", columnDefinition="NUMERIC (12,2)")
	private double value;
	
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

	public MonitoringModel getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(MonitoringModel monitoring) {
		this.monitoring = monitoring;
	}

	public ActivityModel getActivity() {
		return activity;
	}

	public void setActivity(ActivityModel activity) {
		this.activity = activity;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
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