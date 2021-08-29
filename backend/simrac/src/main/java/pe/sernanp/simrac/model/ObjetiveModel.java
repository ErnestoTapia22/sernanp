package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class ObjetiveModel extends BaseModel {
	
	private PlanMasterModel _planMaster;
	private ComponentModel _component;
	
	public PlanMasterModel getPlanMaster() {
		return _planMaster;
	}

	public void setPlanMaster(PlanMasterModel planMaster) {
		this._planMaster = planMaster;
	}
	
	public ComponentModel getComponent() {
		return _component;
	}

	public void setComponent(ComponentModel component) {
		this._component = component;
	}
}
