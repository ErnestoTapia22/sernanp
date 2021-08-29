package pe.sernanp.simrac.model;

import javax.persistence.Entity;

@Entity
public class ModuleModel extends BaseModel {
	
	private int _level;
	private int _moduleid;
	private int _order;
	private int _flag;
	private String _path;
	
	
	
	public int getLevel () {
		return _level;
	}
	
	public void setLevel (int value) {
		_level = value;
	}
	
	public int getModuleid () {
		return _moduleid;
	}
	
	public void setModuleid (int value) {
		_moduleid = value;
	}
	
	public int getOrder () {
		return _order;
	}
	
	public void setOrder (int value) {
		_order = value;
	}
	
	public int getflag () {
		return _flag;
	}
	
	public void setFlag (int value) {
		_flag = value;
	}
	
	public String getPath () {
		return _path;
	}
	
	public void setPath (String value) {
		_path = value;
	}

}
