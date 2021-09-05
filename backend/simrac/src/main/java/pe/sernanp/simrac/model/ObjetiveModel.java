package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class ObjetiveModel extends BaseModel {
	
	private MasterPlanModel _planMaster;
	private ComponentModel _component;
	
	public MasterPlanModel getMasterPlan() {
		return _planMaster;
	}

	public void setMasterPlan(MasterPlanModel planMaster) {
		this._planMaster = planMaster;
	}
	
	public ComponentModel getComponent() {
		return _component;
	}

	public void setComponent(ComponentModel component) {
		this._component = component;
	}
}
