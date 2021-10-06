package pe.sernanp.simrac.model;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name = "rol", schema="sernanp")
public class RoleModel {

	@Column (name= "idrol")
	@Id
	@GeneratedValue(generator = "SEQ_RECORD_JPA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SEQ_RECORD_JPA", sequenceName = "sernanp.rol_idrol_seq", allocationSize = 1)
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
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
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
