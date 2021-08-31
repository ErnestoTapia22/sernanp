package pe.sernanp.simrac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.UserModel;
import pe.sernanp.simrac.repository.ModuleRepository;
import pe.sernanp.simrac.repository.UserRepository;
import pe.sernanp.simrac.model.ModuleModel;
import java.util.List;

@Service
public class UserService extends BaseService<UserModel> {

	@Autowired
	private UserRepository _repository;
	
	@Autowired
	private ModuleRepository _repositoryModule;
	
	
	public ResponseEntity<UserModel> validate(String id) throws Exception {
		try {
		
			boolean success = true;
			ResponseEntity<UserModel> response = new ResponseEntity<UserModel>();
			UserModel item = this._repository.validate(this._dataSource, id);
			List<ModuleModel> items = this._repositoryModule.search(this._dataSource, item.getSystem(), item.getId2());				
			item.setModules(items);
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
