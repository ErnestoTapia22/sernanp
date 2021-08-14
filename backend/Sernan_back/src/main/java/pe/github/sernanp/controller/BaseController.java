package pe.github.sernanp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.github.sernanp.entity.ResponseEntity;
import pe.github.sernanp.model.BaseModel;
import pe.github.sernanp.service.BaseService;

import java.io.IOException;

@RestController
public abstract class BaseController<TEntity extends BaseModel, TService extends BaseService<TEntity>> {
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<TEntity> list() throws IOException {
		return null;
	}
}
