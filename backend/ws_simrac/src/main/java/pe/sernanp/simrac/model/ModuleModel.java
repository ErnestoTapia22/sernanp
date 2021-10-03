package pe.sernanp.simrac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table (name = "modulo", schema="sernanp")
public class ModuleModel {	

	@Column (name= "idmodulo")
	@Id
	private int _id;
	
	@Column (name= "nombremodulo")
	private String _name;
	
	@Column (name= "nivelmodulo")
	private int _level;
	
	@Column (name= "niveldependemodulo")
	private int _moduleid;
	
	@Column (name= "ordenmodulo")
	private int _order;
	
	@Column (name= "flagmodulo")
	private int _flag;
	
	@Column (name= "hrefmodulo")
	private String _path;
			
	public int getId() {
		return _id;
	}

	public void setId(int _id) {
		this._id = _id;
	}

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

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
	
	public void setOrder(int value) {
		_order = value;
	}
	
	public int getFlag () {
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
