package pe.sernanp.simrac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.UserModel;
import pe.sernanp.simrac.repository.UserRepository;

@Service
public class UserService extends BaseService<UserModel> {

	@Autowired
	private UserRepository _repository;
	
	
	public ResponseEntity<UserModel> validate(String id) throws Exception {
		try {
		
			boolean success = true;
			ResponseEntity<UserModel> response = new ResponseEntity<UserModel>();
			UserModel item = this._repository.validate(this._dataSource, id).get(0);
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
