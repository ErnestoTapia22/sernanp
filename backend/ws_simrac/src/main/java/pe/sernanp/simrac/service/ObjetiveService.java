package pe.sernanp.simrac.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.ActionLineModel;
import pe.sernanp.simrac.model.MasterPlanModel;
import pe.sernanp.simrac.model.ObjetiveModel;
import pe.sernanp.simrac.repository.ActionLineRepository;
import pe.sernanp.simrac.repository.ObjetiveRepository;

@Service
public class ObjetiveService {

	@Autowired
	private ObjetiveRepository _repository;
	
	@Autowired
	private ActionLineRepository _repository2;
	
	public ResponseEntity save (ObjetiveModel item) throws Exception{
		try {
			Integer id = item.getId();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			item.setRegistrationDate(item.getRegistrationDate());
			if (id == 0) {
				ObjetiveModel item2 = this._repository.save(item);
				id = item2.getId();
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
			throw new Exception(ex.getMessage());
			
		}
	}
	

	public ResponseEntity<ObjetiveModel> list() throws Exception{
		try {
			ResponseEntity<ObjetiveModel> response = new ResponseEntity<ObjetiveModel>();
			List<ObjetiveModel> items = _repository.findAll();
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
	
	public ResponseEntity<ObjetiveModel> detail(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<ObjetiveModel> response = new ResponseEntity<ObjetiveModel>();
			ObjetiveModel item = _repository.findById(id).get();
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}	
	
	public ResponseEntity<ActionLineModel> searchActionLines(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<ActionLineModel> response = new ResponseEntity<ActionLineModel>();
			List<ActionLineModel> item = this._repository2.searchActionLines(id);
			response.setSuccess(success);
			response.setItems(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}