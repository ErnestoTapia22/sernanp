package pe.github.sernanp.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.NotImplementedException;
import org.locationtech.jts.io.WKBReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.github.sernanp.model.BaseModel;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


@Repository
public abstract class BaseRepository<TEntity extends BaseModel> {

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
	
	public List<TEntity> List() {
		List<TEntity> items = new ArrayList<TEntity>();	
		return items;
	} 
	
	public List<TEntity> list(DataSource ds) throws Exception {
		
		throw new Exception("no implementado");
	}
	
	protected List<TEntity> list(DataSource ds, String storedProcedure, String id,
			RowMapper<TEntity> mapper) throws Exception {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pid", id);
		return this.list(ds, storedProcedure, parameters, mapper);
	}

	protected List<TEntity> list(DataSource ds, String storedProcedure, RowMapper<TEntity> mapper)
			throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate);
		this.setStoredProcedure(jdbc, storedProcedure);
		jdbc.returningResultSet("list", mapper);
		Map<String, Object> results = jdbc.execute();
		@SuppressWarnings("unchecked")
		List<TEntity> items = (List<TEntity>) results.get("list");
		//List<TEntity> items = (List<TEntity>) results.get();
      
		return items;
	}

	protected List<TEntity> list(DataSource ds, String storedProcedure, Map<String, Object> parameters,
			RowMapper<TEntity> mapper) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate);
		this.setStoredProcedure(jdbc, storedProcedure);
		jdbc.returningResultSet("list", mapper);

		SqlParameterSource sqlParameters = new MapSqlParameterSource(parameters);

		Map<String, Object> results = jdbc.execute(sqlParameters);
		@SuppressWarnings("unchecked")
		List<TEntity> items = (List<TEntity>) results.get("list");
		return items;
	}
	
	protected void setStoredProcedure(SimpleJdbcCall jdbc, String storedProcedure) throws Exception {
        String[] partes = storedProcedure.split(Pattern.quote("."));
        //jdbc.withoutProcedureColumnMetaDataAccess();
        switch (partes.length) {
            case 3:
                jdbc.withCatalogName(partes[0]);
                jdbc.withSchemaName(partes[1]);
                jdbc.withProcedureName(partes[2]);
                break;
            case 2:
                jdbc.withSchemaName(partes[0]);
                jdbc.withProcedureName(partes[1]);
                break;
            case 1:
                jdbc.withProcedureName(partes[0]);
                break;
            default:
                throw new Exception("El procedimiento almacenado no es válido");
        }
        jdbc.setFunction(true);
    }
}
