package pe.sernanp.simrac.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.repository.AnpRepository;

@Service
public class AnpService extends BaseService<AnpModel> {
	
	@Autowired
	private AnpRepository _repository;	
	
	@Override
	public ResponseEntity<AnpModel> list() throws Exception{
		try {
			ResponseEntity<AnpModel> response = new ResponseEntity<AnpModel>();
			List<AnpModel> items = this._repository.list(this._dataSource);
			response.setItems(items);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public ResponseEntity<AnpModel> search(AnpModel item, PaginatorEntity paginator) throws Exception{
		try {
			ResponseEntity<AnpModel> response = new ResponseEntity<AnpModel>();
			List<AnpModel> items = this._repository.search(this._dataSource, item, paginator);
			response.setItems(items);
			response.setPaginator(paginator);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}		

}
