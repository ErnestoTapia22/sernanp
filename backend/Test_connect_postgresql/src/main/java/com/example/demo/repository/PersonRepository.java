package com.example.demo.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

@Repository
public class PersonRepository {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	
	public int InsertQuery(Person persona) {
		String sql = "INSERT INTO persona2 (nombre, apellido, correo) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(sql, persona.getName(), persona.getLastName(), persona.getEmail());
         
        return result;
	}
	
	
	public int Insert(Person person) {
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withFunctionName("persona2_insert");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", null);
		params.put("pname", person.getName());
		params.put("plastname", person.getLastName());
		params.put("page", person.getAge());
		params.put("pemail", person.getEmail());

		final Map<String, Object> result = simpleJdbcCall.execute(params);
        return (Integer) result.get("returnvalue");
	}
	
	public int Update(Person person) {
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withFunctionName("persona2_update");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", person.getId());
		params.put("pname", person.getName());
		params.put("plastname", person.getLastName());
		params.put("page", person.getAge());
		params.put("pemail", person.getEmail());

		final Map<String, Object> result = simpleJdbcCall.execute(params);
        return (Integer) result.get("returnvalue");
	}
	
	
	public List<Person> List()
	{
		List<Person> persons = new ArrayList<Person>();
		Person person;	
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
			CallableStatement proc = conn.prepareCall("{? = call public.persona2_list() }");
			proc.registerOutParameter(1, Types.OTHER);
			proc.execute();
			
			ResultSet results = (ResultSet) proc.getObject(1);
			while (results.next())
			{
				person = new Person();
				person.setId(results.getInt("id"));
				person.setName(results.getString("name"));
				person.setLastName(results.getString("lastname"));
				person.setAge(results.getInt("age"));
				person.setEmail(results.getString("email"));
				persons.add(person);
			}
			results.close();
			proc.close();
		} catch (Exception e) {

		}
		
		return persons;
	}
	
}
