package pe.sernanp.simrac.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.ComponentModel;
import pe.sernanp.simrac.repository.ComponentRepository;

@Service
public class ComponentService {

	@Autowired
	private ComponentRepository _repository;


	public ResponseEntity save (ComponentModel item) throws Exception{
		try {
			Integer id = item.getId();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;

			if (id == 0) {
				ComponentModel item2 = this._repository.save(item);
				id = item2.getId();
				message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
						: " Se guardaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			} else {
				this._repository.save(item);
				message += "Se actualizaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			}
			
			ResponseEntity respuesta = new ResponseEntity();
			respuesta.setExtra(id.toString());
			respuesta.setMessage(message);
			respuesta.setSuccess(success);
			return respuesta;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
			
		}
	}
	

	public ResponseEntity<ComponentModel> list() throws Exception{
		try {
			ResponseEntity<ComponentModel> response = new ResponseEntity<ComponentModel>();
			List<ComponentModel> items = _repository.findAll();
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
	
	public ResponseEntity<ComponentModel> detail(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<ComponentModel> response = new ResponseEntity<ComponentModel>();
			ComponentModel item = _repository.findById(id).get();
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}		
}