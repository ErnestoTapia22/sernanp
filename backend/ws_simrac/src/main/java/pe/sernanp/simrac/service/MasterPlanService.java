package pe.sernanp.simrac.service;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.MasterPlanModel;
import pe.sernanp.simrac.model.ObjetiveModel;
import pe.sernanp.simrac.repository.MasterPlanRepository;
import pe.sernanp.simrac.repository.ObjetiveRepository;

@Service
public class MasterPlanService {
	@Autowired
	private MasterPlanRepository _repository;
	
	@Autowired
	private ObjetiveRepository _repository2;
	
	@Transactional
	public ResponseEntity save (MasterPlanModel item) throws Exception{
		try {
			Integer id = item.getId();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			item.setRegistrationDate(item.getRegistrationDate());
			this._repository.updatePlanActive(item.getAnp().getId());
			if (id == 0) {
				MasterPlanModel item2 = this._repository.save(item);
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

	public ResponseEntity<MasterPlanModel> list() throws Exception{
		try {
			ResponseEntity<MasterPlanModel> response = new ResponseEntity<MasterPlanModel>();
			List<MasterPlanModel> items = _repository.findAll();
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
	
	public ResponseEntity<MasterPlanModel> detail(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<MasterPlanModel> response = new ResponseEntity<MasterPlanModel>();
			MasterPlanModel item = _repository.findById(id).get();
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}		
	
	public ResponseEntity<ObjetiveModel> searchObjetives(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<ObjetiveModel> response = new ResponseEntity<ObjetiveModel>();
			List<ObjetiveModel> item= this._repository2.searchObjetives(id);
			response.setSuccess(success);
			response.setItems(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public ResponseEntity<MasterPlanModel> searchByANP(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<MasterPlanModel> response = new ResponseEntity<MasterPlanModel>();
			MasterPlanModel item = this._repository.searchByANP(id);
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}