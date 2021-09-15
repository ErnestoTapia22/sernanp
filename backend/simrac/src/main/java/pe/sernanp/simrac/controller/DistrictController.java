package pe.sernanp.simrac.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.model.DistrictModel;
import pe.sernanp.simrac.model.UserModel;
import pe.sernanp.simrac.service.AnpService;
import pe.sernanp.simrac.service.DistrictService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/district")
public class DistrictController extends BaseController<DistrictModel, DistrictService>{
		
	@Autowired
	private DistrictService _service;
	
	@RequestMapping(value = "/listdepartment")
	@ResponseBody
	public ResponseEntity<DistrictModel> listDepartment() {
		ResponseEntity<DistrictModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}
	
	@RequestMapping(value = "/searchbydepartment/{code}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<DistrictModel> searchByDepartment(@PathVariable("code") String code) throws IOException {
		try {
			ResponseEntity<DistrictModel> response = this._service.searchByDepartment(code);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/searchbyprovince/{code}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<DistrictModel> searchByProvince(@PathVariable("code") String code) throws IOException {
		try {
			ResponseEntity<DistrictModel> response = this._service.searchByProvince(code);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
}
