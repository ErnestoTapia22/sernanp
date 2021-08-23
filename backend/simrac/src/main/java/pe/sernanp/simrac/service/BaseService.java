package pe.sernanp.simrac.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.sernanp.simrac.model.BaseModel;
import pe.sernanp.simrac.model.EconomicActivityModel;
import pe.sernanp.simrac.repository.BaseRepository;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.sql.DataSource;

@Service
public class BaseService<TEntity extends BaseModel> implements IBaseService {

	protected final Logger _logger = LoggerFactory.getLogger(this.getClass());
	protected static String _contextPath;
	protected static String _basePath;
	protected static String _data;
	
	@Autowired
    protected DataSource _dataSource;	
	
	public static void setPathBase(String pathBase) {
		BaseService._basePath = pathBase;
		BaseService._data = BaseService._basePath +  "wwwroot" + File.separator + "data" + File.separator;
	}
	public static void setContextPath(String value) {
		BaseService._contextPath = value;
	}
	
	public ResponseEntity<TEntity> list() throws Exception {
		throw new Exception("No implementado");
	}
    public ResponseEntity<TEntity> list(Boolean withDefault) throws Exception {
		throw new Exception("No implementado");
	}
    
    @SuppressWarnings("unused")
	protected ResponseEntity<TEntity> list(BaseRepository<TEntity> repository, String selectionText) throws Exception {
		try {
			List<TEntity> items = repository.list(this._dataSource);
			return null;// this.list(items, Text);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	protected void add(List<TEntity> items, Class<?> classs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		@SuppressWarnings("unchecked")
		TEntity item = (TEntity) classs.getDeclaredConstructor().newInstance();
		item.setId(0);
		item.setName("Todos");
		items.add(0, item);
	}
	protected <TEntity2 extends BaseModel> void add2(List<TEntity2> items, Class<?> classs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		@SuppressWarnings("unchecked")
		TEntity2 item = (TEntity2) classs.getDeclaredConstructor().newInstance();
		item.setId(0);
		item.setName("Todos");
		items.add(0, item);
	}
	

	@Autowired
	protected PlatformTransactionManager transactionManager = null;
	
	protected void transaction(Consumer<? super TEntity> action) throws SQLException {
		Objects.requireNonNull(action);
		TransactionDefinition definition = null;
		TransactionStatus status = null;
		try {
			definition = new DefaultTransactionDefinition();
			status = this.transactionManager.getTransaction(definition);
			action.accept(null);
			this.transactionManager.commit(status);
		} catch (DataAccessException e) {
			if (this.transactionManager != null) {
				this.transactionManager.rollback(status);
			}
		} catch (Exception e) {

		} catch (Throwable e) {

		} finally {

		}
	}
	
	public ResponseEntity<TEntity> search(TEntity item, PaginatorEntity paginator) throws Exception {
		throw new Exception("No implementado");
	}
}
