package pe.sernanp.simrac.model;

import java.util.List;
import javax.persistence.Entity;

@Entity
public class MonitoringModel extends BaseModel {
	
	private String _comment;
	private String _achievement;
	private String _recommendation;
	private String _evaluation;
	private boolean _active;
	private List<ActivityModel> _activities;

	public String getComment() {
		return _comment;
	}

	public void setComment(String comment) {
		this._comment = comment;
	}
	
	public String getAchievement() {
		return _achievement;
	}

	public void setAchievement(String achievement) {
		this._achievement = achievement;
	}
	
	public String getRecommendation() {
		return _recommendation;
	}

	public void setRecommendation(String recommendation) {
		this._recommendation = recommendation;
	}
	public String getEvaluation() {
		return _evaluation;
	}

	public void setEvaluation(String evaluation) {
		this._evaluation = evaluation;
	}
	
	public boolean getActive() {
		return _active;
	}

	public void setActive(boolean active) {
		this._active = active;
	}
	
	public List<ActivityModel> getActivities() {
		return _activities;
	}
		
	public void setActivities(List<ActivityModel> activities) {
		_activities = activities;		
	}
}
