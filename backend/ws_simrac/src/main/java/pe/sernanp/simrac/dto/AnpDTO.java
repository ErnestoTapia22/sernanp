package pe.sernanp.simrac.dto;

public class AnpDTO {
	
	private int anp_id;	
	
	private String name;
	
	private String _category;
	
	private String _district;
	
	private String _code;
	
	private int _withMasterPlan;
	
	public int getAnp_Id() {
		return anp_id;
	}

	public void setAnp_Id(int anp_id) {
		this.anp_id = anp_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return _category;
	}

	public void setCategory(String _category) {
		this._category = _category;
	}

	public String getDistrict() {
		return _district;
	}

	public void setDistrict(String _district) {
		this._district = _district;
	}

	public String getCode() {
		return _code;
	}

	public void setCode(String _code) {
		this._code = _code;
	}

	public int getWithMasterPlan() {
		return _withMasterPlan;
	}

	public void setWithMasterPlan(int _withMasterPlan) {
		this._withMasterPlan = _withMasterPlan;
	}	
}
