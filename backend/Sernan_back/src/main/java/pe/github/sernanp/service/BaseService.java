package pe.github.sernanp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

import pe.github.sernanp.entity.ResponseEntity;
import pe.github.sernanp.model.BaseModel;
import pe.github.sernanp.repository.BaseRepository;

import javax.sql.DataSource;

@Service
public class BaseService<TEntity extends BaseModel> {

	//@Autowired
	//private BaseRepository<TEntity> _repository;

	@Autowired
	@Qualifier("dataSource")
    protected DataSource _dataSource;
	
	/*public List<TEntity> List() {
		List<TEntity> items = null;
		try {
			items = this._repository.List();
		} catch (Exception ex) {

		}
		return items;
	}*/
	
	public ResponseEntity<TEntity> list() throws Exception {
		throw new Exception("No implementado");
	}
}
