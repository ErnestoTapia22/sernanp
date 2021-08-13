package pe.github.sernan.model;
import java.sql.Date;
public class AgreementState extends Master {
	public AgreementState() {
		
	}
	public AgreementState(int id,String name, String description, Date registration, Boolean state) {
		setId(id);
		setName(name);
		setDescription(description);
		setRegistration(registration);
		setState(state);
	}
	
}
