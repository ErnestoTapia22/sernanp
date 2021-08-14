package pe.github.sernanp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import pe.github.sernanp.entity.ResponseEntity;
import pe.github.sernanp.model.BaseModel;
import java.io.File;
import javax.sql.DataSource;

@Service
public class BaseService<TEntity extends BaseModel> {

	protected final Logger _logger = LoggerFactory.getLogger(this.getClass());
	protected static String _contextPath;
	protected static String _basePath;
	protected static String _data;
	
	@Autowired
	@Qualifier("dataSource")
    protected DataSource _dataSource;
	
	@Autowired
	protected PlatformTransactionManager transactionManager = null;
	
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
}
