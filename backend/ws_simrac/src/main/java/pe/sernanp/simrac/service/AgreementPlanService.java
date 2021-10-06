package pe.sernanp.simrac.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.AgreementPlanModel;
import pe.sernanp.simrac.repository.AgreementPlanRepository;

@Service
public class AgreementPlanService {

	@Autowired
	private AgreementPlanRepository _repository;
	
	public ResponseEntity save (AgreementPlanModel item) throws Exception {
		try {
			Integer id = 0;
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			item.setRegistrationDate(item.getRegistrationDate());
			if (id == 0) {
				AgreementPlanModel item2 = this._repository.save(item);
				id = 0;
				message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
						: " Se guardaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			} else {
				this._repository.save(item);
				message += "Se actualizaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			}
			
			ResponseEntity response = new ResponseEntity();
			response.setExtra(id.toString());
			response.setMessage(message);
			response.setSuccess(success);
			return response;
		} catch (Exception ex) {
			return null;			
		}
	}
	

	public ResponseEntity<AgreementPlanModel> list() throws Exception{
		try {
			ResponseEntity<AgreementPlanModel> response = new ResponseEntity<AgreementPlanModel>();
			List<AgreementPlanModel> items = _repository.findAll();
			response.setItems(items);
			return response;			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	
	public ResponseEntity delete (int id) throws Exception  {
		try {
			this._repository.deleteById(id);
			ResponseEntity response = new ResponseEntity();
			response.setMessage("Se ha eliminado correctamente");
			response.setSuccess(true);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public ResponseEntity<AgreementPlanModel> detail(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<AgreementPlanModel> response = new ResponseEntity<AgreementPlanModel>();
			AgreementPlanModel item = _repository.findById(id).get();
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}