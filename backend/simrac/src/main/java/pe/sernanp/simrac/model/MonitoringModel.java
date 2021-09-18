package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class MonitoringModel extends BaseModel {
	
	private String _comment;

	public String getComment() {
		return _comment;
	}

	public void setComment(String comment) {
		this._comment = comment;
	}
}
