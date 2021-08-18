package pe.sernanp.simrac.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.AlliedCategoryModel;
import pe.sernanp.simrac.service.AlliedCategoryService;

@RestController
@RequestMapping(value = "/alliedcategory")
public class AlliedCategoryController extends BaseController<AlliedCategoryModel, AlliedCategoryService> {

	@Autowired
	private AlliedCategoryService _service;
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public ResponseEntity<AlliedCategoryModel> list() {
		ResponseEntity<AlliedCategoryModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<AlliedCategoryModel> search(@RequestParam("item") String item) throws IOException {
		try {
			PaginatorEntity paginador = super.setPaginator();
			AlliedCategoryModel item2 = super.fromJson(item, AlliedCategoryModel.class);
			ResponseEntity<AlliedCategoryModel> response = _service.search(item2, paginador);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody AlliedCategoryModel item) throws IOException {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<?> delete(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<?> response = this._service.delete(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<AlliedCategoryModel> detail(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<AlliedCategoryModel> response = this._service.detail(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
}
