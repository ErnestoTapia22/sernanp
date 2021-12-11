package pe.sernanp.simrac.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.TransactionScoped;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table (name = "personanatural", schema="sernanp")
public class PersonNaturalModel {

	@Column (name= "idpersonanatural")
	@Id
	private int id;
	
	@Column (name= "idpersona", nullable=true)
	private int personId;
	
	@Column (name= "nombre", nullable=true)
	private String name;
	
	@Column (name= "apepat", nullable=true)
	private String lastName;
	
	@Column (name= "apemat", nullable=true)
	private String maindenName;
	
	@Column (name= "idtipodocumento", nullable=true)
	private int documentTypeId;
	
	@Column (name= "numerodoc", nullable=true)
	private String documentNumber;
	
	@Column (name= "fechanacimiento", nullable=true)
	private Date birthDate;	
	
	@Column (name= "idsexo", nullable=true)
	private int sexId;	
	
	@Column (name= "idestadocivil", nullable=true)
	private int civilStatusId;
	
	@Transient
	@Column (name= "temporal", nullable=true)
	private int temporal;
	
	@Column (name= "ruc", nullable=true)
	private String ruc;
	
	@Column (name= "direccion", nullable=true)
	private String address;
	
	@Column (name= "idubigeo", nullable=true)
	private int ubigeoId;
	
	@Column (name= "var_num_file", nullable=true)
	private String fileNumber;
	
	@Column (name= "var_num_caja", nullable=true)
	private String boxNumber;
	
	@Column (name= "var_numero_pasaporte", nullable=true)
	private String passportNumber;
	
	@Column (name= "var_numero_brevete", nullable=true)
	private String briefNumber;
	
	@Transient
	@Column (name= "int_id_tipo", nullable=true)
	private int typeId;
	
	@Transient
	@Column (name= "int_id_ubigeo_nacimiento", nullable=true, columnDefinition= "INTEGER")
	private int int_id_ubigeo_nacimiento;

	public int getInt_id_ubigeo_nacimiento() {
		return int_id_ubigeo_nacimiento;
	}

	public void setInt_id_ubigeo_nacimiento(int int_id_ubigeo_nacimiento) {
		this.int_id_ubigeo_nacimiento = int_id_ubigeo_nacimiento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMaindenName() {
		return maindenName;
	}

	public void setMaindenName(String maindenName) {
		this.maindenName = maindenName;
	}

	public int getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(int documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getSexId() {
		return sexId;
	}

	public void setSexId(int sexId) {
		this.sexId = sexId;
	}

	public int getCivilStatusId() {
		return civilStatusId;
	}

	public void setCivilStatusId(int civilStatusId) {
		this.civilStatusId = civilStatusId;
	}

	public int getTemporal() {
		return temporal;
	}

	public void setTemporal(int temporal) {
		this.temporal = temporal;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getUbigeoId() {
		return ubigeoId;
	}

	public void setUbigeoId(int ubigeoId) {
		this.ubigeoId = ubigeoId;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getBriefNumber() {
		return briefNumber;
	}

	public void setBriefNumber(String briefNumber) {
		this.briefNumber = briefNumber;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
}
