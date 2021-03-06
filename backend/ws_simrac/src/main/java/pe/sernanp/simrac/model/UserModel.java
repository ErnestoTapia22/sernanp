package pe.sernanp.simrac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table (name = "usuario", schema="sernanp")
public class UserModel {

	@Column (name= "idusuario")
	@Id
	private int id;
	
	@Column (name= "usuario")
	private String userName;
	
	@Column (name= "idpersonal")
	private int _idPersonal;	
	
	@Column (name= "idpersona")
	private int idPerson;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIdPersonal() {
		return _idPersonal;
	}

	public void setIdPersonal(int _idPersonal) {
		this._idPersonal = _idPersonal;
	}

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int person) {
		this.idPerson = person;
	}
	
}
