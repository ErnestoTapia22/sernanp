package pe.github.sernanp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.github.sernanp.entity.ResponseEntity;
import pe.github.sernanp.model.EconomicActivityModel;
import pe.github.sernanp.model.PersonModel;
import pe.github.sernanp.repository.EconomicActivityRepository;

@Service
public class EconomicActivityService extends BaseService<EconomicActivityModel> {

	
	@Autowired
	private EconomicActivityRepository _repository;
		
	
	@Override
	public ResponseEntity<EconomicActivityModel> list() throws Exception{
		try {
			ResponseEntity<EconomicActivityModel> response = new ResponseEntity<EconomicActivityModel>();
			List<EconomicActivityModel> items = this._repository.list(this._dataSource);
			response.setItems(items);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
