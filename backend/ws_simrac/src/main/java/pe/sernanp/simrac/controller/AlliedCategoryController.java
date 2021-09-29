package pe.sernanp.simrac.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.AlliedCategoryModel;
import pe.sernanp.simrac.model.Persona;
import pe.sernanp.simrac.service.AlliedCategoryService;

@RestController
@RequestMapping ("/api/alliedcategory")
public class AlliedCategoryController {
	
	@Autowired
	private AlliedCategoryService _service;
	
	@RequestMapping(value = "/list")
	@GetMapping
	private ResponseEntity<AlliedCategoryModel> List () {
		ResponseEntity<AlliedCategoryModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;	
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	private ResponseEntity<?> save (@RequestBody AlliedCategoryModel item) {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return null;
		}		
	}
		
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody()
	private ResponseEntity<?> delete (@PathVariable("id") int id) {
		try {
			ResponseEntity<?> response = this._service.delete(id);
			return response;
		} catch (Exception ex) {
			return null;
		}
	}
		
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody()
	private ResponseEntity<AlliedCategoryModel> detail (@PathVariable ("id") int id) {
		try {
			ResponseEntity<AlliedCategoryModel> response = this._service.detail(id);
			return response;
		} catch(Exception ex) {
			return null;
		}
	}	
}