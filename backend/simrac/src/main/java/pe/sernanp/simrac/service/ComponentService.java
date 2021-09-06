package pe.sernanp.simrac.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.ComponentModel;
import pe.sernanp.simrac.repository.ComponentRepository;

@Service
public class ComponentService extends BaseService<ComponentModel> {
	
	@Autowired
	private ComponentRepository _repository;
	
	@Override
	public ResponseEntity<ComponentModel> list() throws Exception{
		try {
			ResponseEntity<ComponentModel> response = new ResponseEntity<ComponentModel>();
			List<ComponentModel> items = this._repository.list(this._dataSource);
			response.setItems(items);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}		
}
