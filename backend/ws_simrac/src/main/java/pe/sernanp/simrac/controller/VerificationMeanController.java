package pe.sernanp.simrac.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.VerificationMeanModel;
import pe.sernanp.simrac.service.VerificationMeanService;

@RestController
@RequestMapping ("/api/verificationmean")
public class VerificationMeanController {
	
	@Autowired
	private VerificationMeanService _service;
	
	@RequestMapping(value = "/list")
	@GetMapping
	private ResponseEntity<VerificationMeanModel> List () {
		ResponseEntity<VerificationMeanModel> response = new ResponseEntity<>();
		try {
			response = this._service.list();
		} catch (Exception ex) {
			response.setMessage(ex);
		}
		return response;	
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	private ResponseEntity<?> save (@RequestBody VerificationMeanModel item) {
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
	private ResponseEntity<VerificationMeanModel> detail (@PathVariable ("id") int id) {
		try {
			ResponseEntity<VerificationMeanModel> response = this._service.detail(id);
			return response;
		} catch(Exception ex) {
			return null;
		}
	}	
}