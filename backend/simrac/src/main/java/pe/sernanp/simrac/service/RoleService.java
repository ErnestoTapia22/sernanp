package pe.sernanp.simrac.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.AlliedCommitmentModel;
import pe.sernanp.simrac.model.RoleModel;
import pe.sernanp.simrac.repository.RoleRepository;

@Service
public class RoleService extends BaseService<RoleModel> {
	
	@Autowired
	private RoleRepository _repository;
	
	
	public ResponseEntity<RoleModel> search(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<RoleModel> response = new ResponseEntity<RoleModel>();
			List<RoleModel> item = this._repository.search(this._dataSource, id);
			response.setSuccess(success);
			response.setItems(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity save(RoleModel item) throws Exception {		
		TransactionDefinition definition = null;
		TransactionStatus status = null;
		try {
			Integer id = item.getId2();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);
			if (id == 0) {
				id = this._repository.insert(this._dataSource, item);
				message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
						: " Se guardaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			}
			
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
				throw new Exception("El c??digo ingresado ya se encuentra registrado.");
			else
				throw new Exception(ex.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity save2(RoleModel item) throws Exception {		
		TransactionDefinition definition = null;
		TransactionStatus status = null;
		try {
			Integer id = item.getId2();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);
			this._repository.deleteModule(this._dataSource, item.getId2());
			item.getModules().forEach( (module) -> {				
				try {
					this._repository.insert2(this._dataSource, item.getId2(), module.getId2(), item.getRegistrationDate());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//throw new Exception("El c??digo ingresado ya se encuentra registrado.");
					e.printStackTrace();					
				}
			});
			
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
				throw new Exception("Error al guardar los datos");
			else
				throw new Exception(ex.getMessage());
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Transactional
	public ResponseEntity delete(int id) throws Exception {
		TransactionDefinition definition = null;
		TransactionStatus status = null;
		try {
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);			
			Integer rowsAffected = this._repository.delete(this._dataSource, id);
			this.transactionManager.commit(status);
			ResponseEntity response = new ResponseEntity();

			response.setMessage("Se ha eliminado correctamente");
			response.setSuccess(true);
			return response;
		} catch (Exception ex) {
			if (this.transactionManager != null) {
				if (status != null)
					this.transactionManager.rollback(status);
			}
			throw new Exception(ex.getMessage());
		}
	}
}