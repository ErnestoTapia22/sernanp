package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class ResultModel extends BaseModel {

	private PlanJobModel _planJob;
	private String _comment;
	
	public PlanJobModel getPlanJob() {
		return _planJob;
	}

	public void setPlanJob(PlanJobModel planJob) {
		this._planJob = planJob;
	}	

	public String getComment() {
		return _comment;
	}

	public void setComment(String comment) {
		this._comment = comment;
	}
}
