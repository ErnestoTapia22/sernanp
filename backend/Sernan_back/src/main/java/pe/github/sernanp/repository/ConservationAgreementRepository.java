package pe.github.sernanp.repository;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.io.WKBReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.github.sernanp.mapper.ConservationAgreementMapper;
import pe.github.sernanp.mapper.PersonMapper;
import pe.github.sernanp.model.AgreementStateModel;
import pe.github.sernanp.model.ConservationAgreementModel;
import pe.github.sernanp.model.PersonModel;

@Repository
public class ConservationAgreementRepository extends BaseRepository<ConservationAgreementModel> {

	@Autowired
	JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	
	/* @Override
	public int Insert(ConservationAgreement conservationAgreement) 
	{
		return super.Insert("public.ConservationAgreement_insert", conservationAgreement);
	}
	
	@Override
	public int Update(ConservationAgreement conservationAgreement) 
	{
		return super.Update("public.ConservationAgreement_update", conservationAgreement);
	}
	
	*/
	
	/*@Override
	public List<ConservationAgreementModel> list(DataSource ds) throws Exception {
		return super.list(ds, "simrac.fn_list_acuerdo_conservacion", new ConservationAgreementMapper());
	}*/
	
	
	public List<ConservationAgreementModel> List() {
		List<ConservationAgreementModel> persons = new ArrayList<ConservationAgreementModel>();
		ConservationAgreementModel conservationAgreement;
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
			CallableStatement proc = conn.prepareCall("{? = call simrac.fn_list_acuerdo_conservacion() }");
			proc.registerOutParameter(1, Types.OTHER);
			proc.execute();

			ResultSet results = (ResultSet) proc.getObject(1);
			while (results.next()) {
				conservationAgreement = new ConservationAgreementModel();
				conservationAgreement.setId(results.getInt("srl_id"));
				AgreementStateModel aState = new AgreementStateModel();
				aState.setName(results.getString("txt_agreementstatename"));
				conservationAgreement.setAgreementState(aState);
				conservationAgreement.setTypeecosystemid(results.getString("int_tipoecosistemaid"));
				conservationAgreement.setFirm(results.getDate("dt_fec_firma"));
				conservationAgreement.setValidity(results.getInt("int_vig"));
				conservationAgreement.setState(results.getBoolean("bol_flg"));
				conservationAgreement.setName(results.getString("var_nom"));
				conservationAgreement.setCategory(results.getString("var_cat"));
				conservationAgreement.setCode(results.getString("num_cod"));

				persons.add(conservationAgreement);
			}
			results.close();
			proc.close();
		} catch (Exception e) {

		}

		return persons;
	}
	
	@Override
	public List<ConservationAgreementModel> list(DataSource ds) throws Exception {
		List<ConservationAgreementModel> persons = new ArrayList<ConservationAgreementModel>();
		ConservationAgreementModel conservationAgreement;
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
			CallableStatement proc = conn.prepareCall("{? = call simrac.fn_list_acuerdo_conservacion() }");
			proc.registerOutParameter(1, Types.OTHER);
			proc.execute();

			ResultSet results = (ResultSet) proc.getObject(1);
			while (results.next()) {
				conservationAgreement = new ConservationAgreementModel();
				conservationAgreement.setId(results.getInt("srl_id"));
				AgreementStateModel aState = new AgreementStateModel();
				aState.setName(results.getString("txt_agreementstatename"));
				conservationAgreement.setAgreementState(aState);
				conservationAgreement.setTypeecosystemid(results.getString("int_tipoecosistemaid"));
				conservationAgreement.setFirm(results.getDate("dt_fec_firma"));
				conservationAgreement.setValidity(results.getInt("int_vig"));
				conservationAgreement.setState(results.getBoolean("bol_flg"));
				conservationAgreement.setName(results.getString("var_nom"));
				conservationAgreement.setCategory(results.getString("var_cat"));
				conservationAgreement.setCode(results.getString("num_cod"));

				persons.add(conservationAgreement);
			}
			results.close();
			proc.close();
		} catch (Exception e) {

		}

		return persons;
	}
}
