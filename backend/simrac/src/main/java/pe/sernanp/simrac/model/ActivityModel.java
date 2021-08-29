package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class ActivityModel extends BaseModel {

	private PlanJobModel _planJob;
	private CommitmentModel _commitment;
	private int _goal;
	private int _value;
	private String _indicator;
	
	public PlanJobModel getPlanJob() {
		return _planJob;
	}

	public void setPlanJob(PlanJobModel planJob) {
		this._planJob = planJob;
	}
	
	public CommitmentModel getCommitment() {
		return _commitment;
	}

	public void setCommitment(CommitmentModel commitment) {
		this._commitment = commitment;
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
	
	public int getGoal() {
		return _goal;
	}

	public void setGoal(int goal) {
		this._goal = goal;
	}
}
