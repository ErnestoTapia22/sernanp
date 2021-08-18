package pe.sernanp.simrac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.entity.ResponseEntity;
import pe.gisriv.extension.JsonMapper;
import pe.sernanp.simrac.model.BaseModel;
import pe.sernanp.simrac.service.BaseService;

import java.io.IOException;
import org.springframework.context.ApplicationContext;
import javax.servlet.http.HttpServletRequest;

@RestController
public abstract class BaseController<TEntity extends BaseModel, TService extends BaseService<TEntity>> {
			
	@Autowired
	protected HttpServletRequest _request;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<TEntity> list() throws IOException {
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	protected ResponseEntity getJSON(Exception ex) {
		// _logger.error("Exception!!!", ex);
		ResponseEntity response = new ResponseEntity();
		response.setMessage(ex.getMessage());
		response.setSuccess(false);
		return response;
	}
	
	protected PaginatorEntity setPaginator() throws Exception {
		String[] valores = _request.getParameterValues("paginator");
		if (valores != null && valores.length > 0)
			return this.<PaginatorEntity> fromJson2(valores[0], PaginatorEntity.class);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	protected <TEntity2 extends BaseModel> TEntity2 fromJson(String json, Class<?> classs) throws IOException {
		JsonMapper mapper = new JsonMapper();
		return (TEntity2) mapper.readValue(json, classs);
	}

	@SuppressWarnings("unchecked")
	protected <TEntity3> TEntity3 fromJson2(String json, Class<?> classs) throws IOException {
		JsonMapper mapper = new JsonMapper();
		return (TEntity3) mapper.readValue(json, classs);
	}
}
