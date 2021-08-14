package pe.github.sernanp.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.github.sernanp.mapper.EconomicActivityMapper;
import pe.github.sernanp.mapper.PersonMapper;
import pe.github.sernanp.model.ConservationAgreementModel;
import pe.github.sernanp.model.EconomicActivityModel;
import pe.github.sernanp.model.PersonModel;

@Repository
public class EconomicActivityRepository extends BaseRepository<EconomicActivityModel> {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;	
	
	@Override
	public List<EconomicActivityModel> list(DataSource ds) throws Exception {
		List<EconomicActivityModel> persons = new ArrayList<EconomicActivityModel>();
		EconomicActivityModel economicactivity;	
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
			CallableStatement proc = conn.prepareCall("{? = call simrac.fn_list_actividadeconomica() }");
			proc.registerOutParameter(1, Types.OTHER);
			proc.execute();
			
			ResultSet results = (ResultSet) proc.getObject(1);
			while (results.next())
			{
				economicactivity = new EconomicActivityModel();
				economicactivity.setId(results.getInt("id"));
				economicactivity.setName(results.getString("name"));
				economicactivity.setDescription(results.getString("descrption"));
				//economicactivity.setRegistrationDate(results.getTimestamp("registrationdate"));
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
