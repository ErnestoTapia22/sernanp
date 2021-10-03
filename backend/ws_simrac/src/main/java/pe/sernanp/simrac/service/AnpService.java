package pe.sernanp.simrac.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.sernanp.simrac.entity.PaginatorEntity;
import pe.sernanp.simrac.entity.ResponseEntity;
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.repository.AnpRepository;

@Service
public class AnpService {

	@Autowired
	private AnpRepository _repository;

	public ResponseEntity<AnpModel> list() throws Exception{
		try {
			ResponseEntity<AnpModel> response = new ResponseEntity<AnpModel>();
			List<AnpModel> items = _repository.findAll();
			response.setItems(items);
			return response;			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}	
	
	public ResponseEntity delete(int id) throws Exception  {
		try {
			this._repository.deleteById(id);
			ResponseEntity response = new ResponseEntity();
			response.setMessage("Se ha eliminado correctamente");
			response.setSuccess(true);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public ResponseEntity<AnpModel> search(AnpModel item, PaginatorEntity paginator) throws Exception{
		try {			
			Pageable page = PageRequest.of(paginator.getOffset()-1, paginator.getLimit());
			Page<AnpModel> pag = this._repository.findAll(item.getCode(), item.getName(), page);
			List<AnpModel> items = pag.getContent();
			paginator.setTotal((int)pag.getTotalElements());
			ResponseEntity<AnpModel> response = new ResponseEntity<AnpModel>();
			response.setItems(items);
			response.setPaginator(paginator);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}		

}