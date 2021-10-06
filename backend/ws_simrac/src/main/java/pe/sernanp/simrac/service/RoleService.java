package pe.sernanp.simrac.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.simrac.dto.RoleDTO;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.RoleModel;
import pe.sernanp.simrac.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository _repository;	
	
	public ResponseEntity<RoleModel> search(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<RoleModel> response = new ResponseEntity<RoleModel>();
			List<RoleModel> item = this._repository.search(id);
			response.setSuccess(success);
			response.setItems(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}	
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity save(RoleModel item) throws Exception {				
		try {
			Integer id = item.getId();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			item.setRegistrationDate(item.getRegistrationDate());
			if (id == 0) {
				RoleModel item2 = this._repository.save(item);
				id = item2.getId();
				message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
						: " Se guardaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			}
			ResponseEntity response = new ResponseEntity();
			response.setExtra(id.toString());
			response.setMessage(message);
			response.setSuccess(success);
			return response;
		} catch (Exception ex) {			
			if (ex instanceof org.springframework.dao.DuplicateKeyException)
				throw new Exception("El código ingresado ya se encuentra registrado.");
			else
				throw new Exception(ex.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity save2(RoleDTO item) throws Exception {
		try {
			Integer id = item.getId();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			this._repository.deleteModule(item.getId());
			item.getModules().forEach( (module) -> {
				try {
					this._repository.insert2(item.getId(), module.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
					: " Se guardaron sus datos de manera correcta";
			success = (id == 0) ? false : true;
			ResponseEntity response = new ResponseEntity();
			response.setExtra(id.toString());
			response.setMessage(message);
			response.setSuccess(success);
			return response;
		} catch (Exception ex) {			
			if (ex instanceof org.springframework.dao.DuplicateKeyException)
				throw new Exception("Error al guardar los datos");
			else
				throw new Exception(ex.getMessage());
		}
	}	
	
	@SuppressWarnings({ "rawtypes", "unused" })		
	@Transactional
	public ResponseEntity delete(int id) throws Exception {		
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

}