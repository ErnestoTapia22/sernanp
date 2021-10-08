package pe.sernanp.simrac.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.sernanp.simrac.dto.DistrictDTO;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.DistrictModel;
import pe.sernanp.simrac.service.DistrictService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/district")
public class DistrictController extends BaseController {
		
	@Autowired
	private DistrictService _service;
	
	@RequestMapping(value = "/listdepartment")
	@ResponseBody
	public ResponseEntity<DistrictDTO> listDepartment() {
		ResponseEntity<DistrictDTO> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}
	
	@RequestMapping(value = "/searchbydepartment/{code}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<DistrictDTO> searchByDepartment(@PathVariable("code") String code) throws IOException {
		try {
			ResponseEntity<DistrictDTO> response = this._service.searchByDepartment(code);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/searchbyprovince/{code}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<DistrictDTO> searchByProvince(@PathVariable("code") String code) throws IOException {
		try {
			ResponseEntity<DistrictDTO> response = this._service.searchByProvince(code);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
}
