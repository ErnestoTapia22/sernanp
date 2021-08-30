package pe.sernanp.simrac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.ModuleModel;
import pe.sernanp.simrac.repository.ModuleRepository;

@Service
public class ModuleService extends BaseService<ModuleModel> {

	@Autowired
	private ModuleRepository _repository;
		
	public ResponseEntity<ModuleModel> search(int id, int id2) throws Exception {
		try {
		
			boolean success = true;
			ResponseEntity<ModuleModel> response = new ResponseEntity<ModuleModel>();
			List <ModuleModel>  item  = this._repository.search(this._dataSource, id, id2);
			response.setSuccess(success);
			response.setItems(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
