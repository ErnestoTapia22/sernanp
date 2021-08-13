package pe.github.sernan.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pe.github.sernan.model.EconomicActivity;

@Repository
public class EconomicActivityRepository {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;

	
	public List<EconomicActivity> List()
	{
		List<EconomicActivity> persons = new ArrayList<EconomicActivity>();
		EconomicActivity economicactivity;	
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
			CallableStatement proc = conn.prepareCall("{? = call simrac.fn_list_actividadeconomica() }");
			proc.registerOutParameter(1, Types.OTHER);
			proc.execute();
			
			ResultSet results = (ResultSet) proc.getObject(1);
			while (results.next())
			{
				economicactivity = new EconomicActivity();
				economicactivity.setId(results.getInt("id"));
				economicactivity.setName(results.getString("name"));
				economicactivity.setDescription(results.getString("descrption"));
				economicactivity.setRegistratioDate(results.getTimestamp("registratiodate"));
				economicactivity.setState(results.getBoolean("state"));
				persons.add(economicactivity);
			}
			results.close();
			proc.close();
		} catch (Exception e) {

		}
		
		return persons;
	}	
		
	
}
