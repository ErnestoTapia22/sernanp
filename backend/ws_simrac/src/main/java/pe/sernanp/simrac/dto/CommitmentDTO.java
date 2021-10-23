package pe.sernanp.simrac.dto;

import java.util.Calendar;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.AlliedModel;
import pe.sernanp.simrac.model.ConservationAgreementModel;

public class CommitmentDTO implements java.io.Serializable {
	
	private int id;	
	
	private ConservationAgreementModel ConservationAgreement;	
	
	private AlliedModel allied;	
	
	private ActionLineModel actionLine;
	
	private String description;
	
	private Date registrationDate;
	
	private Boolean state;
	
	private String indicator;	
	
	private Boolean active;	
	
	private Double progress = 0.0;
	
	private int rowspan;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ConservationAgreementModel getConservationAgreement() {
		return ConservationAgreement;
	}

	public void setConservationAgreement(ConservationAgreementModel conservationAgreement) {
		ConservationAgreement = conservationAgreement;
	} 

	public AlliedModel getAllied() {
		return allied;
	}

	public void setAllied(AlliedModel allied) {
		this.allied = allied;
	}

	public ActionLineModel getActionLine() {
		return actionLine;
	}

	public void setActionLine(ActionLineModel actionLine) {
		this.actionLine = actionLine;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRegistrationDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String _indicator) {
		this.indicator = _indicator;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	@JsonFormat(pattern=".##")
	public double getProgress () {
		return progress;
	}
	
	public void setProgress (int value, int goal) {
		progress = ((value * 100.00 ) / goal);
	}
	
	public void setProgress (double value) {
		progress = value;
	}
	
	public double getRowspan () {
		return rowspan;
	}
	
	public void setRowspan (int value) {
		rowspan = value;
	}
	
}