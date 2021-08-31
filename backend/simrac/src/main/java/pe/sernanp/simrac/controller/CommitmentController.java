package pe.sernanp.simrac.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.AlliedModel;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.service.CommitmentService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/commitment/")
public class CommitmentController extends BaseController<CommitmentModel, CommitmentService> {

	
	@Autowired
	private CommitmentService _service;
		
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
	public ResponseEntity<CommitmentModel> detail(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<CommitmentModel> response = this._service.detail(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody()
	public ResponseEntity<?> save(@RequestBody CommitmentModel item) throws IOException {
		try {
			ResponseEntity<?> response = this._service.save(item);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
	
	@RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
	@ResponseBody()
	public ResponseEntity<CommitmentModel> search(@PathVariable("id") int id) throws IOException {
		try {
			ResponseEntity<CommitmentModel> response = this._service.search(id);
			return response;
		} catch (Exception ex) {
			return super.getJSON(ex);
		}
	}
}
