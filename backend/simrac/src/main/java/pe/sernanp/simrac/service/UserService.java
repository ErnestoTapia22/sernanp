package pe.sernanp.simrac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.UserModel;
import pe.sernanp.simrac.repository.ModuleRepository;
import pe.sernanp.simrac.repository.UserRepository;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.model.ModuleModel;

import java.util.List;

@Service
public class UserService extends BaseService<UserModel> {

	@Autowired
	private UserRepository _repository;
	
	@Autowired
	private ModuleRepository _repositoryModule;
	
	
	public ResponseEntity<UserModel> validate(String id) throws Exception {
		try {
		
			boolean success = true;
			ResponseEntity<UserModel> response = new ResponseEntity<UserModel>();
			UserModel item = this._repository.validate(this._dataSource, id);
			List<ModuleModel> items = this._repositoryModule.search(this._dataSource, item.getSystem(), item.getId2());				
			item.setModules(items);
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public ResponseEntity<UserModel> search(UserModel item, PaginatorEntity paginator) throws Exception{
		try {
			ResponseEntity<UserModel> response = new ResponseEntity<UserModel>();
			List<UserModel> items = this._repository.search(this._dataSource, item, paginator);
			response.setItems(items);
			response.setPaginator(paginator);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public ResponseEntity<UserModel> searchWithoutLogin(String dni, int system) throws Exception {
		try {
		
			boolean success = true;
			ResponseEntity<UserModel> response = new ResponseEntity<UserModel>();
			List <UserModel>  item  = this._repository.searchWithoutLogin(this._dataSource, dni, system);
			response.setSuccess(success);
			response.setItems(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity save(UserModel item) throws Exception {		
		TransactionDefinition definition = null;
		TransactionStatus status = null;
		try {
			Integer id = item.getId2();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);
			id = this._repository.insert(this._dataSource, item);
			message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
					: " Se guardaron sus datos de manera correcta";
			success = (id == 0) ? false : true;
			this.transactionManager.commit(status);
			ResponseEntity respuesta = new ResponseEntity();
			respuesta.setExtra(id.toString());
			respuesta.setMessage(message);
			respuesta.setSuccess(success);
			return respuesta;
		} catch (Exception ex) {
			if (this.transactionManager != null) {
				if (status != null)
					this.transactionManager.rollback(status);
			}
			if (ex instanceof org.springframework.dao.DuplicateKeyException)
				throw new Exception("El código ingresado ya se encuentra registrado.");
			else
				throw new Exception(ex.getMessage());
		}
	}
	
}
