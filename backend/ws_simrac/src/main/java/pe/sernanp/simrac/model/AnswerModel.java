package pe.sernanp.simrac.model;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table (name = "t_respuesta", indexes = {@Index(name = "idx_respuesta", columnList = "srl_id",unique = true)})
public class AnswerModel {

	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/* @Column (name= "int_actividadid", columnDefinition="INTEGER")
	private ActivityModel activity;
	
	@Column (name= "int_monitoreoid", columnDefinition="INTEGER")
	private MonitoringModel monitoring;  */
	
	@Column (name= "int_valor", columnDefinition="INTEGER")
	private int value;
	
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

	/*public ActivityModel getActivity() {
		return activity;
	}

	public void setActivity(ActivityModel activity) {
		this.activity = activity;
	}

	public MonitoringModel getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(MonitoringModel monitoring) {
		this.monitoring = monitoring;
	}  */

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
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