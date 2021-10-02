package pe.sernanp.simrac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.json.JsonMapper;
import pe.sernanp.simrac.entity.PaginatorEntity;
import pe.sernanp.simrac.entity.ResponseEntity;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

@RestController
public abstract class BaseController {
			
	@Autowired
	protected HttpServletRequest _request;
		
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
	protected <TEntity2> TEntity2 fromJson(String json, Class<?> classs) throws IOException {
		JsonMapper mapper = new JsonMapper();
		return (TEntity2) mapper.readValue(json, classs);
	}

	@SuppressWarnings("unchecked")
	protected <TEntity3> TEntity3 fromJson2(String json, Class<?> classs) throws IOException {
		JsonMapper mapper = new JsonMapper();
		return (TEntity3) mapper.readValue(json, classs);
	}
}
