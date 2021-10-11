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
import javax.persistence.Table;

@Entity
@Table (name = "t_actividad", indexes = {@Index(name = "idx_actividad", columnList = "srl_id",unique = true)})
public class ActivityModel {
	
	@Column (name= "srl_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@JoinColumn (name= "int_compromisoid", referencedColumnName = "srl_id", nullable=false, foreignKey = @ForeignKey(name="fk_actividad_compromiso"))
	@ManyToOne
	private CommitmentModel commitment;
	
	@JoinColumn (name= "int_plantrabajoid", referencedColumnName = "srl_id", nullable=false, foreignKey = @ForeignKey(name="fk_actividad_plantrabajo"))
	@ManyToOne
	private WorkPlanModel workPlan;
	
	@Column (name= "var_nom", columnDefinition="TEXT", nullable=false)
	private String name;
	
	@Column (name= "txt_des", columnDefinition="TEXT")
	private String description;
	
	@Column (name= "tsp_fec", columnDefinition= "TIMESTAMP WITHOUT TIME ZONE", nullable=false)
	private Date registrationDate;
	
	@Column (name= "bol_flg", nullable=false)	
	private Boolean state;	
	
	@Column (name= "int_meta")	
	private int goal;	
	
	@Column (name= "bol_activo")	
	private Boolean active;	
	
	@Column (name= "var_indicador", length=200)
	private String indicator;
	
	@Column (name= "var_semestre", length=50)	
	private String semester;	
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public CommitmentModel getCommitment() {
		return commitment;
	}
	public void setCommitment(CommitmentModel commitment) {
		this.commitment = commitment;
	}
	
	public WorkPlanModel getWorkPlan() {
		return workPlan;
	}
	public void setWorkPlan(WorkPlanModel workPlan) {
		this.workPlan = workPlan;
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
	public int getGoal() {
		return goal;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	
}