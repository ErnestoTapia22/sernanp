package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class ActivityModel extends BaseModel {

	private WorkPlanModel _workPlan;
	private CommitmentModel _commitment;
	private int _goal;
	private int _value;
	private String _indicator;
	private boolean _active;
	private String _semester;
	private float _progress;
	
	private MonitoringModel _monitoring;
	
	public WorkPlanModel getWorkPlan() {
		return _workPlan;
	}

	public void setWorkPlan(WorkPlanModel workPlan) {
		this._workPlan = workPlan;
	}
	
	public CommitmentModel getCommitment() {
		return _commitment;
	}

	public void setCommitment(CommitmentModel commitment) {
		this._commitment = commitment;
	}	

	public int getGoal() {
		return _goal;
	}

	public void setGoal(int goal) {
		this._goal = goal;
	}
	
	public int getValue() {
		return _value;
	}

	public void setValue(int value) {
		this._value = value;
	}
	
	public String getIndicator() {
		return _indicator;
	}

	public void setIndicator(String indicator) {
		this._indicator = indicator;
	}
	
	public boolean getActive() {
		return _active;
	}

	public void setActive(boolean active) {
		this._active = active;
	}
	
	public String getSemester() {
		return _semester;
	}

	public void setSemester(String semester) {
		this._semester = semester;
	}
	
	public MonitoringModel getMonitoring() {
		return _monitoring;
	}

	public void setMonitoring(MonitoringModel monitoring) {
		this._monitoring = monitoring;
	}
	
	public double getProgress() {
		if (this._goal != 0 && this._value != 0)
			return (this._value * 100.00 ) / this._goal;
		else 
			return 0;
	}

}
