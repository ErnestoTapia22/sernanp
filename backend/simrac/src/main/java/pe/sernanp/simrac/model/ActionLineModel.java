package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class ActionLineModel extends BaseModel {
	
	private ObjetiveModel _objetive;
	
	public ObjetiveModel getObjetive() {
		return _objetive;
	}

	public void setObjetive(ObjetiveModel objetive) {
		this._objetive = objetive;
	}
}
