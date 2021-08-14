package pe.github.sernanp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import pe.github.sernanp.entity.PaginatorEntity;
import pe.github.sernanp.entity.ResponseEntity;
import pe.github.sernanp.model.EconomicActivityModel;
import pe.github.sernanp.model.PersonModel;
import pe.github.sernanp.repository.EconomicActivityRepository;

@Service
public class EconomicActivityService extends BaseService<EconomicActivityModel> {
	
	@Autowired
	private EconomicActivityRepository _repository;
	
	@Override
	public ResponseEntity<EconomicActivityModel> list() throws Exception{
		try {
			ResponseEntity<EconomicActivityModel> response = new ResponseEntity<EconomicActivityModel>();
			List<EconomicActivityModel> items = this._repository.list(this._dataSource);
			response.setItems(items);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public ResponseEntity<EconomicActivityModel> search(EconomicActivityModel item, PaginatorEntity paginator) throws Exception{
		try {
			ResponseEntity<EconomicActivityModel> response = new ResponseEntity<EconomicActivityModel>();
			List<EconomicActivityModel> items = this._repository.search(this._dataSource, item, paginator);
			response.setItems(items);
			response.setPaginator(paginator);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	public ResponseEntity<EconomicActivityModel> find() throws Exception{
		try {
			System.out.println(this._dataSource);
			boolean success = true;
			ResponseEntity<EconomicActivityModel> response = new ResponseEntity<EconomicActivityModel>();
			List<EconomicActivityModel> items = this._repository.find(this._dataSource);
			response.setSuccess(success);
			response.setItems(items);
			return response;
			
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	@SuppressWarnings({ "rawtypes", "unused" })
	// @Transactional
	public ResponseEntity save(EconomicActivityModel item) throws Exception {
		String workspace = "";// ConfigManager.getAppSettings().get("workspace");
		String baseSourcePath = String.format("%s/%s/%s/", BaseService._basePath, "wwwroot", "data");
		String baseTargetPath = String.format("%s/%s/", workspace, "files");
		TransactionDefinition definition = null;
		TransactionStatus status = null;
		try {
			Integer id = item.getId2();
			String message = "";
			boolean success = false;
			int rowsAffected = 0;
			//if(item.getRRPP().getId2()==0)
			//	item.getRRPP().setId(null);
			//if(item.getUEA().getId2() == 0)
			//	item.getUEA().setId(null);
			//if(item.getHolder().getId2()==0)
			//	item.getHolder().setId(null);
			//if(item.getSituationalStatus().getId2() ==0)
			//	item.getSituationalStatus().setId(null);
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);
			if (id == 0) {
				id = _repository.insert(this._dataSource, item);
				message += (id == 0) ? "Ha ocurrido un error al guardar sus datos"
						: " Se guardaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			} else {
				id = _repository.update(this._dataSource, item);
				message += "Se actualizaron sus datos de manera correcta";
				success = (id == 0) ? false : true;
			}
			//if (item.getDocuments() != null && item.getDocuments().size() > 0) {
			//	List<DocumentModel> itemsDocumentDeleted = item.getDocuments().stream().filter(t->t.getId2()>0).collect(Collectors.toList());
			//	for (int i = 0; i < itemsDocumentDeleted.size(); i++) {
			//		DocumentModel t = itemsDocumentDeleted.get(i);
			//		this._repositoryDocument.delete(this._dataSource, t.getId2());
			//	};
			//	List<DocumentModel> itemsDocumentNew = item.getDocuments().stream().filter(t->t.getId2()==0).collect(Collectors.toList());
			//	  for (int i = 0; i < itemsDocumentNew.size(); i++) {
			//			DocumentModel t = itemsDocumentNew.get(i);
			//			String fileName =  System.getProperty("java.io.tmpdir") + t.getGUID() + t.getExtension();
			//			File file = new File(fileName);
			//			FileEntity itemFile = FileEntity.fromFile(file);
			//			t.setContent(itemFile.getContent());
			//			t.setPhysicalLocation(baseTargetPath);
			//			t.setDocument(new DocumentModel());
			//			t.setGUID(t.getGUID());
			//			t.setName(itemFile.getName());
			//			t.setOriginalName(t.getOriginalName());
			//			t.setContentType(itemFile.getContentType());
			//			t.setExtension(itemFile.getExtension());
			//			Integer documentId = this._serviceDocument.save(this._dataSource, t);
			//			this._repository.insertDocument(this._dataSource, id, documentId);
			//	};
			//}
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
	
	@SuppressWarnings({ "rawtypes", "unused" })
	// @Transactional
	public ResponseEntity delete(int id) throws Exception {
		TransactionDefinition definition = null;
		TransactionStatus status = null;
		try {
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);
			Integer filaAfectada = this._repository.deleteDocument(this._dataSource, id);
			//this._serviceDocument.delete(id);
			Integer rowsAffected = _repository.delete(this._dataSource, id);
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
	public ResponseEntity<EconomicActivityModel> detail(int id) throws Exception {
		try {
			if (id == 0) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<EconomicActivityModel> response = new ResponseEntity<EconomicActivityModel>();
			EconomicActivityModel item = this._repository.detail(this._dataSource, id);
			//item.setIsClient(true);
			//List<DocumentModel> itemsDocument = this._repository.findDocuments(this._dataSource, id);
			//item.setDocuments(itemsDocument);
			response.setSuccess(success);
			response.setItem(item);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	public ResponseEntity<EconomicActivityModel> findBy2(EconomicActivityModel item) throws Exception {
		try {
			if (item == null) {
				throw new Exception("No existe el elemento");
			}
			boolean success = true;
			ResponseEntity<EconomicActivityModel> response = new ResponseEntity<EconomicActivityModel>();
			List<EconomicActivityModel> items = this._repository.findBy2(this._dataSource, item);
			//items.forEach(t ->{
			//	t.setIsClient(true);
			//});
			//List<DocumentModel> itemsDocument = this._repository.findDocuments(this._dataSource, id);
			//item.setDocuments(itemsDocument);
			response.setSuccess(success);
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	public ResponseEntity<EconomicActivityModel> findBy(EconomicActivityModel item) throws Exception {
		try {
			Boolean state = item.getState();
			if(state == true) {
				item.setState(false);
			}else {
				item.setState(true);
			}
			List<EconomicActivityModel> items = _repository.findBy(this._dataSource, item);
			ResponseEntity<EconomicActivityModel> response = new ResponseEntity<EconomicActivityModel>();
			response.setItems(items);
			return response;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
