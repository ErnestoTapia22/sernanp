package pe.sernanp.simrac.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.RoleModel;
import pe.sernanp.simrac.repository.RoleRepository;

@Service
public class RoleService extends BaseService<RoleModel> {
	
	@Autowired
	private RoleRepository _repository;
	
	
	public ResponseEntity<RoleModel> search(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<RoleModel> response = new ResponseEntity<RoleModel>();
			List<RoleModel> item = this._repository.search(this._dataSource, id);
			response.setSuccess(success);
			response.setItems(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

}