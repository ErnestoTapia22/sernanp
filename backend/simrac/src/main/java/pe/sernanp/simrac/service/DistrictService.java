package pe.sernanp.simrac.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.DistrictModel;
import pe.sernanp.simrac.repository.DistrictRepository;

@Service
public class DistrictService extends BaseService<DistrictModel> {
	
	@Autowired
	private DistrictRepository _repository;	
	
	@Override
	public ResponseEntity<DistrictModel> list() throws Exception{
		try {
			ResponseEntity<DistrictModel> response = new ResponseEntity<DistrictModel>();
			List<DistrictModel> items = this._repository.list(this._dataSource);
			response.setItems(items);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public ResponseEntity<DistrictModel> searchByDepartment(String code) throws Exception {
		try {
		
			boolean success = true;
			ResponseEntity<DistrictModel> response = new ResponseEntity<DistrictModel>();
			List<DistrictModel> items = this._repository.searchByDepartment(this._dataSource, code);
			response.setSuccess(success);
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}	

	public ResponseEntity<DistrictModel> searchByProvince(String code) throws Exception {
		try {
		
			boolean success = true;
			ResponseEntity<DistrictModel> response = new ResponseEntity<DistrictModel>();
			List<DistrictModel> items = this._repository.searchByProvince(this._dataSource, code);
			response.setSuccess(success);
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
