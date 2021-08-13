package pe.github.sernan.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.github.sernan.model.Master;

@Repository
public class BaseRepository<TEntity extends Master> {

	@Autowired
    JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	
	public Map<String, Object> ConfigParameters(Map<String, Object> params, TEntity entity) {
		params.put("pid", entity.getId());
		params.put("pname", entity.getName());
		params.put("plastname", entity.getLastName());
		params.put("page", entity.getAge());
		params.put("pemail", entity.getEmail());
		params.put("pdescription", entity.getDescription());
		params.put("pstate", entity.getState());
		params.put("pregistration", entity.getRegistration());
		
		//geometry
		//params.put("pgeometry", entity.getGeometryWKB());
		//params.put("psrid", 4326);
		
		return params;
	}
	
	// ######################################### INSERT #########################################
	
	public int Insert(TEntity entity) 
	{
		throw new NotImplementedException("No implementado");
	}
	
	public int Insert(String procedure, TEntity entity) 
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params = this.ConfigParameters(params, entity);
		return UpSertDel(procedure, params);
	}
	
	public int Insert(String procedure, Map<String, Object> params) 
	{
		return UpSertDel(procedure, params);
	}
	
	// ######################################### END INSERT #####################################
	
	// ######################################### UPDATE #########################################
	
	public int Update(TEntity entity) 
	{
		throw new NotImplementedException("No implementado");
	}
	
	public int Update(String procedure, TEntity entity) 
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params = this.ConfigParameters(params, entity);
		return UpSertDel(procedure, params);
	}
	
	public int Update(String procedure, Map<String, Object> params) 
	{
		return UpSertDel(procedure, params);
	}
	
	// ######################################### END UPDATE ######################################
	

	// ######################################### DELETE ##########################################
	
	public int Delete(int id) 
	{
		throw new NotImplementedException("No implementado");
	}
	
	public int Delete(String procedure, int id) 
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", id);
		return UpSertDel(procedure, params);
	}
	
	public int Delete(String procedure, Map<String, Object> params) 
	{
		return UpSertDel(procedure, params);
	}
	
	// ######################################### END DELETE ######################################
	
	
	public int UpSertDel(String procedure, Map<String, Object> params) 
	{
		String[] temp = procedure.split("\\.");
		temp = (temp.length == 1) ? new String[] {"public", temp[0]} : temp;
		
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName(temp[0]).withFunctionName(temp[1]);
		
		final Map<String, Object> result = simpleJdbcCall.execute(params);

        return (Integer) result.get("returnvalue");
	}
		
	// ######################################### SEARCH ######################################
	
//	public <T>T Search(String procedure, int id)
//	{
//		String[] temp = procedure.split("\\.");
//		temp = (temp.length == 1) ? new String[] {"simrac", temp[0]} : temp;
//		
//		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName(temp[0]).withFunctionName(temp[1]);
//		
//		final Map<String,Object> params = new HashMap<>();
//		params.put("id", id);
//		final Map<String, Object> result = simpleJdbcCall.execute(params);
//         return (T) result.get("returnvalue");
//        
//	}
	
	// ######################################### END SEARCH ######################################
	
	
	/*
	public int InsertQuery(Person persona) {
		String sql = "INSERT INTO persona2 (nombre, apellido, correo) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(sql, persona.getName(), persona.getLastName(), persona.getEmail());
         
        return result;
	}
	 */
}
