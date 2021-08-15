package pe.github.sernanp.model;

import java.util.Calendar;
import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

@Entity
public class BaseModel {

	@NotNull
	@Id
	protected Object _id;
	private String _name;
	private String _code;
	
	protected String _observation;
	protected String _description;
	private Boolean _state;
	@JsonIgnore
	private String _stateName;
	@JsonIgnore
	private Date _registrationDate2;
	private Boolean _issynchronized;
	private String _guid;
	@JsonIgnore
	private int _rowsAffected;
	
	public Date getRegistrationDate() {
		Calendar calendar = Calendar.getInstance();
		return (Date) calendar.getTime();
	}
	
	public void setRegistrationDate(Date registration) {
		_registrationDate2 = registration;
	}
	
	public Object getId() {
		return _id;
	}

	public void setId(Object id) {
		_id = id;
	}
	
	public String getCode() {
		return _code;
	}

	public void setCode(String value) {
		_code = value;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}
	
	public String getObservation() {
		return _observation;
	}

	public void setObservation(String value) {
		_observation = value;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String value) {
		_description = value;
	}

	public Boolean getState() {
		return (_state == null) ? false : _state;
	}

	public void setState(Boolean value) {
		_state = value;
	}
	
	@JsonProperty
	public String getStateName() {
		if (StringUtils.isEmpty(this._stateName))
			return (this._state != null && this._state == true) ? "Activo" : "Desactivo";
		return _stateName;
	}

	@JsonIgnore
	public void setStateName(String value) {
		_stateName = value;
	}
	
	public Date getRegistrationDate2() {
		return _registrationDate2;
	}

	@JsonIgnore
	public void setRegistrationDate2(Date value) {
		_registrationDate2 = value;
	}
	
	public Boolean getIssynchronized() {
		return _issynchronized;
	}

	public void setIssynchronized(Boolean value) {
		_issynchronized = value;
	}

	public String getGUID() {
		return _guid;
	}

	public void setGUID(String value) {
		this._guid = value;
	}

	@JsonIgnore
	public int getRowsAffected() {
		return _rowsAffected;
	}

	@JsonIgnore
	public void setRowsAffected(int value) {
		_rowsAffected = value;
	}
	
	@JsonIgnore
	private int _recordstotal;

	@JsonIgnore
	public int getRecordsTotal() {
		return this._recordstotal;
	}

	@JsonIgnore
	public void setRecordsTotal(int value) {
		this._recordstotal = value;
	}
	
	@JsonIgnore
	public Integer getId2() {
		Object id = this.getId();
		if (id == null)
			return 0;
		String patron = "\\d+";
		if (id.toString().matches(patron))
			return Integer.parseInt(id.toString());
		else
			return 0;
	}
	
	@JsonIgnore
	private int _rowNum;

	@JsonIgnore
	public int getRowNum() {
		return this._rowNum;
	}

	@JsonIgnore
	public void setRowNum(int value) {
		this._rowNum = value;
	}
}
