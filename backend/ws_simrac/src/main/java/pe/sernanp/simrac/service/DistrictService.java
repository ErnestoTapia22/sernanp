package pe.sernanp.simrac.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.sernanp.simrac.dto.DistrictDTO;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.DistrictModel;
import pe.sernanp.simrac.repository.DistrictRepository;

@Service
public class DistrictService {

	@Autowired
	private DistrictRepository _repository;	
	
	public ResponseEntity<DistrictDTO> list() throws Exception{
		try {
			ResponseEntity<DistrictDTO> response = new ResponseEntity<DistrictDTO>();
			List<DistrictDTO> items2 = new ArrayList<DistrictDTO>();
			List<DistrictModel> items = this._repository.list();
			items.forEach(item -> {
				DistrictDTO item222 = new DistrictDTO();
				item222.setCode(item.getDepartmentId());
				item222.setName(item.getName());
				items2.add(item222);
			});
			response.setItems(items2);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public ResponseEntity<DistrictDTO> searchByDepartment(String code) throws Exception {
		try {
		
			boolean success = true;
			ResponseEntity<DistrictDTO> response = new ResponseEntity<DistrictDTO>();
			List<DistrictDTO> items2 = new ArrayList<DistrictDTO>();
			List<DistrictModel> items = this._repository.searchByDepartment(code);
			items.forEach(item -> {
				DistrictDTO item222 = new DistrictDTO();
				item222.setCode(item.getDepartmentId()+item.getProvinceId());
				item222.setName(item.getName());
				items2.add(item222);
			});
			response.setSuccess(success);
			response.setItems(items2);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public ResponseEntity<DistrictDTO> searchByProvince(String code) throws Exception {
		try {
		
			boolean success = true;
			ResponseEntity<DistrictDTO> response = new ResponseEntity<DistrictDTO>();
			List<DistrictDTO> items2 = new ArrayList<DistrictDTO>();
			List<DistrictModel> items = this._repository.searchByProvince(code);
			items.forEach(item -> {
				DistrictDTO item222 = new DistrictDTO();
				item222.setCode(item.getDepartmentId()+item.getProvinceId()+item.getDistrictId());
				item222.setName(item.getName());
				items2.add(item222);
			});
			response.setSuccess(success);
			response.setItems(items2);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
