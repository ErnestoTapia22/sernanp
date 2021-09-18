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
}
