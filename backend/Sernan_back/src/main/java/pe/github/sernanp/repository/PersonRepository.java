package pe.github.sernanp.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.locationtech.jts.io.WKBReader;
import org.springframework.stereotype.Repository;

import pe.github.sernanp.mapper.PersonMapper;
import pe.github.sernanp.model.PersonModel;

@Repository
public class PersonRepository extends BaseRepository<PersonModel> {
		
	@Override
	public int Insert(PersonModel person) 
	{
		return super.Insert("public.persona2_insert", person);
	}
	
	@Override
	public int Update(PersonModel person) 
	{
		return super.Update("public.persona2_update", person);
	}
	
	
	/*@Override
	public List<PersonModel> List()
	{
		List<PersonModel> persons = new ArrayList<PersonModel>();
		PersonModel person;	
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
			CallableStatement proc = conn.prepareCall("{? = call geo.persona2_list() }");
			proc.registerOutParameter(1, Types.OTHER);
			proc.execute();
			
			ResultSet results = (ResultSet) proc.getObject(1);
			while (results.next())
			{
				person = new PersonModel();
				person.setId(results.getInt("id"));
				person.setName(results.getString("name"));
				person.setLastName(results.getString("lastname"));
				person.setAge(results.getInt("age"));
				person.setEmail(results.getString("email"));
				
				//Geometry 
				byte[] bytes = results.getBytes("geometry");
				WKBReader readerWKB = new WKBReader();
				person.setGeometry(readerWKB.read(bytes));
				
				persons.add(person);
			}
			results.close();
			proc.close();
		} catch (Exception e) {

		}
		
		return persons;
	}*/
	

	/*public List<TEntity> List()
	{
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
		         .withSchemaName("SCHEMA")
		         .withProcedureName("SP_GET_PERSON_BY_SEGMENT")
		         .declareParameters(
		                 new SqlParameter("P_SEGMENT_ID", Types.VARCHAR))
		         .returningResultSet("RESULT", new PersonRowMapper());
	}*/
		
	@Override
	public List<PersonModel> list(DataSource ds) throws Exception {
		List<PersonModel> items = new ArrayList<PersonModel>();
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
			CallableStatement proc = conn.prepareCall("{? = call simrac.fn_list_acuerdo_conservacion() }");
			proc.registerOutParameter(1, Types.OTHER);
			proc.execute();			
			ResultSet results = (ResultSet) proc.getObject(1);
			PersonMapper mapper = new PersonMapper();
			while (results.next())
			{				
				PersonModel item = mapper.mapRow(results, 0);
				items.add(item);
			}
			results.close();
			proc.close();
		} catch (Exception e) {
		}		
		return items;
	}
	
}
