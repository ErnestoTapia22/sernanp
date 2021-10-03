package pe.sernanp.simrac.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table (name = "rol", schema="sernanp")
public class RoleModel {

	@Column (name= "idrol")
	@Id
	private int id;
	
	@Column (name= "desrol")
	private String name;
	
	@Column (name= "flagrol")
	private String _flag;
	
	@Column (name= "idsistema")
	private int _system;
	
	@Column (name= "tsp_fecha_reg")
	private Date _registrationDate;		
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegistrationDate() {
		return _registrationDate;
	}

	public void setRegistrationDate(Date _registrationDate) {
		this._registrationDate = _registrationDate;
	}

	public int getSystem() {
		return _system;
	}

	public void setSystem(int system) {
		_system = system;
	}
	
	public String getFlag() {
		return _flag;
	}

	public void setFlag(String flag) {
		_flag = flag;
	}
}
