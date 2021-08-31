package pe.sernanp.simrac.repository;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.gisriv.entity.PaginatorEntity;
import pe.gisriv.extension.ResultSetExtension;
import pe.sernanp.simrac.model.BaseModel;

import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@Repository
public abstract class BaseRepository<TEntity extends BaseModel> implements IRepository {
	
	protected JdbcTemplate _jdbcTemplate;	
		
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
	
	protected List<TEntity> list2(DataSource ds, String storedProcedure, RowMapper<TEntity> mapper)
			throws Exception {
		Connection conn = ds.getConnection();
		conn.setAutoCommit(false);
		String functionName = "{? = call " + storedProcedure + "() }";
		CallableStatement proc = conn.prepareCall(functionName);
		proc.registerOutParameter(1, Types.OTHER);
		proc.execute();
		ResultSet results = (ResultSet) proc.getObject(1);
		List<TEntity> items = new ArrayList<TEntity>();
		while (results.next())
		{
			TEntity item = mapper.mapRow(results, 0);
			items.add(item);
		}
		results.close();
		proc.close();
		conn.close();
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
                jdbc.withFunctionName(partes[1]);
                //jdbc.withProcedureName(partes[1]);
                break;
            case 1:
                jdbc.withProcedureName(partes[0]);
                break;
            default:
                throw new Exception("El procedimiento almacenado no es válido");
        }
        jdbc.setFunction(true);
    }
	
	private void setPaginator(Map<String, Object> parameters, PaginatorEntity paginator) {
		parameters.put("pstart", paginator.getOffset());
		parameters.put("plimit", paginator.getLimit());
		parameters.put("pordercolumn", paginator.getSort());
		parameters.put("porderdir", paginator.getOrder());
	}

	private void getPaginator(Map<String, Object> results, PaginatorEntity paginator) {
		@SuppressWarnings("unchecked")
		List<PaginatorEntity> items = (List<PaginatorEntity>) results.get("paginator");
		if (items != null && items.size() > 0) {
			paginator.setTotal(items.get(0).getTotal());
		} else {
			@SuppressWarnings("unchecked")
			List<TEntity> itemsResultado = (List<TEntity>) results.get("list");
			if (itemsResultado != null && itemsResultado.size() > 0) {
				paginator.setTotal(itemsResultado.get(0).getRecordsTotal());
			} else {
				paginator.setTotal(0);
			}
		}
	}

	protected void setParametersSearch(Map<String, Object> parameters, TEntity item) throws Exception {

	}

	protected void setParameters(Map<String, Object> parameters, TEntity item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());
	}
	
	public List<TEntity> search(DataSource ds, TEntity item, PaginatorEntity paginator) throws Exception {
		throw new Exception("no implementado");
	}

	public List<TEntity> search(DataSource ds, TEntity item) throws Exception {
		throw new Exception("no implementado");
	}

	public int insert(DataSource ds, TEntity item) throws Exception {
		throw new Exception("no implementado");
	}

	public TEntity detail(DataSource ds, int id) throws Exception {
		throw new Exception("no implementado");
	}

	public int update(DataSource ds, TEntity item) throws Exception {
		throw new Exception("no implementado");
	}

	public int delete(DataSource ds, int id) throws Exception {
		throw new Exception("no implementado");
	}

	protected List<TEntity> search(DataSource ds, String storedProcedure, Object value,
			RowMapper<TEntity> mapper) throws Exception {
		return this.search(ds, storedProcedure, value, "pid", mapper);
	}

	protected List<TEntity> search(DataSource ds, String storedProcedure, Object value,
			String parameterName, RowMapper<TEntity> mapper) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(parameterName, value);
		return this.search(ds, storedProcedure, parameters, mapper);
	}

	protected List<TEntity> search(DataSource ds, String storedProcedure, TEntity item,
			PaginatorEntity paginator, RowMapper<TEntity> mapper) throws Exception {

		Map<String, Object> parameters = new HashMap<>();
		this.setParametersSearch(parameters, item);
		return this.search(ds, storedProcedure, parameters, paginator, mapper);
	}
	
	protected List<TEntity> search2(DataSource ds, String storedProcedure, Map<String, Object> item, PaginatorEntity paginator, RowMapper<TEntity> mapper) throws Exception {
		Connection conn = ds.getConnection();
		conn.setAutoCommit(false);
		String parametersCount = " (";
		for (int k = 0; k<item.size(); k++) {
			parametersCount += "?,";
	    }
		parametersCount +="?,?,?,?)";
		String functionName = "{? = call " + storedProcedure + parametersCount + "  }";
		CallableStatement proc = conn.prepareCall(functionName);
		proc.registerOutParameter(1, Types.OTHER);
		int i = 2; 
		for (Map.Entry<String, Object> entry : item.entrySet()) {
			proc.setObject(i, entry.getValue());
	        i++;
	    }		
		this.setPaginator(i, paginator, proc);
		proc.execute();
		ResultSet results = (ResultSet) proc.getObject(1);
		List<TEntity> items = new ArrayList<TEntity>();
		Boolean isFirst = false;
		while (results.next())
		{
			if (isFirst == false) {
				paginator.setTotal(ResultSetExtension.getInt2(results, "precordstotal"));
				isFirst = true;
			}				
			TEntity item2 = mapper.mapRow(results, 0);
			items.add(item2);
		}
		results.close();
		proc.close();
		conn.close();
		return items;		
	}

	protected void setPaginator(int position, PaginatorEntity paginator, CallableStatement proc) throws Exception {
		proc.setObject(position++, paginator.getOffset());
		proc.setObject(position++, paginator.getLimit());
		proc.setObject(position++, paginator.getSort());
		proc.setObject(position++, paginator.getOrder());
	}
	protected List<TEntity> search(DataSource ds, String storedProcedure, TEntity item,
			RowMapper<TEntity> mapper) throws Exception {

		Map<String, Object> parameters = new HashMap<>();
		this.setParametersSearch(parameters, item);
		return this.search(ds, storedProcedure, parameters, mapper);
	}

	protected List<TEntity> search(DataSource ds, String storedProcedure, Map<String, Object> parameters,
			PaginatorEntity paginator, RowMapper<TEntity> mapper) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate);
		this.setStoredProcedure(jdbc, storedProcedure);
		jdbc.returningResultSet("list", mapper);
		this.setPaginator(parameters, paginator);
		SqlParameterSource sqlParameters = new MapSqlParameterSource(parameters);
		Map<String, Object> results = jdbc.execute(sqlParameters);
		@SuppressWarnings("unchecked")
		List<TEntity> items = (List<TEntity>) results.get("list");
		this.getPaginator(results, paginator);
		return items;
	}
	
	protected List<TEntity> search(DataSource ds, String storedProcedure, Map<String, Object> parameters,
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

	protected <TEntity2 extends BaseModel> List<TEntity2> search2(DataSource ds, String storedProcedure, Object value,
			RowMapper<TEntity2> mapper) throws Exception {
		return this.search2(ds, storedProcedure, value, "pid", mapper);
	}

	protected <TEntity2 extends BaseModel> List<TEntity2> search2(DataSource ds, String storedProcedure, Object value,
			String parameterName, RowMapper<TEntity2> mapper) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(parameterName, value);
		return this.search23(ds, storedProcedure, parameters, mapper);
	}

	protected <TEntity2 extends BaseModel> List<TEntity2> search2(DataSource ds, String storedProcedure, Map<String, Object> parameters, RowMapper<TEntity2> mapper) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate);
		this.setStoredProcedure(jdbc, storedProcedure);
		jdbc.returningResultSet("list", mapper);
		SqlParameterSource sqlParameters = new MapSqlParameterSource(parameters);
		Map<String, Object> results = jdbc.execute(sqlParameters);
		@SuppressWarnings("unchecked")
		List<TEntity2> items = (List<TEntity2>) results.get("list");
		return items;
	}	
	
	protected <TEntity2 extends BaseModel> List<TEntity2> search23(DataSource ds, String storedProcedure, Map<String, Object> parameters, RowMapper<TEntity2> mapper) throws Exception {
		Connection conn = ds.getConnection();
		conn.setAutoCommit(false);
		String parametersCount = " (";
		for (int k = 0; k<parameters.size(); k++) {
			parametersCount += "?" +  (( k + 1 == parameters.size()) ? "":",");
	    }
		parametersCount += ")";
		String functionName = "{? = call " + storedProcedure + parametersCount + "  }";
		CallableStatement proc = conn.prepareCall(functionName);
		proc.registerOutParameter(1, Types.OTHER);
		int i = 2; 
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			proc.setObject(i, entry.getValue());
	        i++;
	    }		
		proc.execute();
		ResultSet results = (ResultSet) proc.getObject(1);
		List<TEntity2> items = new ArrayList<TEntity2>();
		while (results.next())
		{						
			TEntity2 item2 = mapper.mapRow(results, 0);
			items.add(item2);
		}
		results.close();
		proc.close();
		conn.close();
		return items;
	}

	protected <TEntity3> List<TEntity3> search3(DataSource ds, String storedProcedure,	Map<String, Object> parameters, RowMapper<TEntity3> mapper) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate);
		this.setStoredProcedure(jdbc, storedProcedure);
		jdbc.returningResultSet("list", mapper);
		SqlParameterSource sqlParameters = new MapSqlParameterSource(parameters);
		Map<String, Object> results = jdbc.execute(sqlParameters);
		@SuppressWarnings("unchecked")
		List<TEntity3> items = (List<TEntity3>) results.get("list");
		return items;
	}

	protected TEntity detail(DataSource ds,String storedProcedure, Object id, RowMapper<TEntity> mapper) throws Exception {

		Map<String, Object> parameters = new HashMap<String, Object>(1);
		parameters.put("pid", id);
		return this.detail(ds, storedProcedure, parameters, mapper);
	}

	protected TEntity detail(DataSource ds, String storedProcedure, Map<String, Object> parameters,	RowMapper<TEntity> mapper) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate);
		this.setStoredProcedure(jdbc, storedProcedure);
		jdbc.returningResultSet("list", mapper);
		SqlParameterSource sqlParameters = new MapSqlParameterSource(parameters);
		Map<String, Object> results = jdbc.execute(sqlParameters);
		@SuppressWarnings("unchecked")
		List<TEntity> items = (List<TEntity>) results.get("list");
		TEntity item = null;
		if (items.size() > 0) {
			item = items.get(0);
		}
		return item;
	}

	protected <TEntity2 extends BaseModel> TEntity2 detail2(JdbcTemplate jdbcTemplate,String storedProcedure, Object id, RowMapper<TEntity2> mapper) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pid", id);
		return this.detail2(jdbcTemplate, storedProcedure, parameters, mapper);
	}

	protected <TEntity2 extends BaseModel> TEntity2 detail2(DataSource ds, String storedProcedure, Map<String, Object> parameters, RowMapper<TEntity2> mapper) throws Exception {		
		Connection conn = ds.getConnection();
		conn.setAutoCommit(false);
		String functionName = "{? = call " + storedProcedure + "(?) }";
		CallableStatement proc = conn.prepareCall(functionName);
		proc.registerOutParameter(1, Types.OTHER);
		int i = 2;
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			proc.setObject(i, entry.getValue());
	        i++;
	    }
		proc.execute();
		TEntity2 item = null;
		proc.execute();
		ResultSet results = (ResultSet) proc.getObject(1);
		while (results.next())
		{
			item = mapper.mapRow(results, 0);
		}	
		results.close();
		proc.close();
		conn.close();
		return item;
	}
	
	protected <TEntity2 extends BaseModel> TEntity2 detail2(DataSource ds, String storedProcedure, int id, RowMapper<TEntity2> mapper) throws Exception {		
		Connection conn = ds.getConnection();
		conn.setAutoCommit(false);
		String functionName = "{? = call " + storedProcedure + "(?) }";
		CallableStatement proc = conn.prepareCall(functionName);
		proc.registerOutParameter(1, Types.OTHER);
		proc.setInt(2, id);
		proc.execute();
		TEntity2 item = null;
		proc.execute();
		ResultSet results = (ResultSet) proc.getObject(1);
		while (results.next())
		{
			item = mapper.mapRow(results, 0);
		}	
		results.close();
		proc.close();
		conn.close();
		return item;
	}
	
	protected String findValue(DataSource ds,String storedProcedure, Object id) throws Exception {

		Map<String, Object> parameters = new HashMap<String, Object>(1);
		parameters.put("pkey", id);
		return this.findValue(ds, storedProcedure, parameters);
	}
	protected String findValue(DataSource ds, String storedProcedure, Map<String, Object> parameters) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate);
		this.setStoredProcedure(jdbc, storedProcedure);
		SqlParameterSource sqlParameters = new MapSqlParameterSource(parameters);
		String value = jdbc.executeObject(String.class, sqlParameters);
		return value;
	}
	@SuppressWarnings("unused")
	protected int insert(DataSource ds, String storedProcedure, Map<String, Object> parameters)
			throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate);
		this.setStoredProcedure(jdbc, storedProcedure);
		SqlParameterSource sqlParameters = new MapSqlParameterSource(parameters);
		Map<String, Object> results = jdbc.execute(sqlParameters);
		int id = 0;
		int rowsAffected=0;
		if(results.get("pid")!= null)
			id = Integer.parseInt(results.get("pid").toString());
		if(results.get("prowsaffected")!= null)
			rowsAffected = Integer.parseInt(results.get("prowsaffected").toString());
		return id;
	}
	protected int insert(DataSource ds, String storedProcedure, TEntity item) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		this.setParameters(parameters, item);
		return this.insert(ds, storedProcedure, parameters);
	}

	protected int update(DataSource ds, String storedProcedure, Map<String, Object> parameters)
			throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate);
		this.setStoredProcedure(jdbc, storedProcedure);
		SqlParameterSource sqlParameters = new MapSqlParameterSource(parameters);
		Map<String, Object> results = jdbc.execute(sqlParameters);
		int id = 0;
		int rowsAffected=0;
		if(results.get("pid")!= null)
			id = Integer.parseInt(results.get("pid").toString());
		if(results.get("prowsaffected")!= null)
			rowsAffected = Integer.parseInt(results.get("prowsaffected").toString());
		return id;
	}

	protected int update(DataSource ds, String storedProcedure, TEntity item) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		this.setParameters(parameters, item);

		return this.update(ds, storedProcedure, parameters);
	}

	protected int upsert(DataSource ds, String storedProcedure, Map<String, Object> parameters)
			throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate);
		this.setStoredProcedure(jdbc, storedProcedure);
		SqlParameterSource sqlParameters = new MapSqlParameterSource(parameters);
		Map<String, Object> results = jdbc.execute(sqlParameters);
		int id = 0;
		if (results.get("pid") != null) {
			id = Integer.parseInt(results.get("pid").toString());
		}
		return id;
	}

	protected int delete(DataSource ds,String storedProcedure, Object value) throws Exception {
		return this.delete(ds, storedProcedure, value, "pid");
	}

	protected int delete(DataSource ds,String storedProcedure, Object value, String parameterName) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(parameterName, value);
		return this.delete(ds, storedProcedure, parameters);
	}

	@SuppressWarnings("unused")
	protected int delete(DataSource ds, String storedProcedure, Map<String, Object> parameters)
			throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate);
		this.setStoredProcedure(jdbc, storedProcedure);
		SqlParameterSource sqlParameters = new MapSqlParameterSource(parameters);
		Map<String, Object> results = jdbc.execute(sqlParameters);
		int id = 0;
		if (results.get("pid") != null) {
			id = Integer.parseInt(results.get("pid").toString());
		}
		int rowsAffected = Integer.parseInt(results.get("prowsaffected").toString());
		return rowsAffected;
	}
	
}
