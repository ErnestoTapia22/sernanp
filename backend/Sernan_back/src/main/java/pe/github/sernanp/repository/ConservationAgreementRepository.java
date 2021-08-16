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
import org.springframework.stereotype.Repository;

import pe.github.sernanp.mapper.ConservationAgreementMapper;
import pe.github.sernanp.model.AgreementStateModel;
import pe.github.sernanp.model.ConservationAgreementModel;

@Repository
public class ConservationAgreementRepository extends BaseRepository<ConservationAgreementModel> {	
		
	public List<ConservationAgreementModel> List() {
		List<ConservationAgreementModel> persons = new ArrayList<ConservationAgreementModel>();
		ConservationAgreementModel conservationAgreement;
		try {
			Connection conn = _jdbcTemplate.getDataSource().getConnection();
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
			Connection conn = ds.getConnection();
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
	public int insert(DataSource ds, ConservationAgreementModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_actividadeconomica", item);
	}

	@Override
	public int update(DataSource ds, ConservationAgreementModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_actividadeconomica", item);
	}
	
	public List<ConservationAgreementModel> find(DataSource ds) throws Exception{
		System.out.println(ds);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pid",2);
		return super.search2(ds,"mc.miningconcession_search2",parameters, new ConservationAgreementMapper());
	}
	
	public List<ConservationAgreementModel> findBy(DataSource ds, ConservationAgreementModel item) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pcode", item.getCode());
		parameters.put("pname", item.getName());
		//parameters.put("pcertificatetitle", item.getCertificateTitle());
		parameters.put("pstate", item.getState());
		return null;
		//return super.search(ds, "mc.miningconcession_findby", parameters, new EconomicActivityMapper());
	}	
	
	public int deleteDocument(DataSource ds, int id) throws Exception {
		return super.delete(ds, "mc.miningconcession_deletedocument", id);
	}
	
	public int insertDocument(DataSource ds, int id, int documentId)
			throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pminingconcessionid", id);
		parameters.put("pdocumentid", documentId);
		return super.insert(ds, "mc.miningconcession_insertdocument", parameters);
	}

	//public List<DocumentModel> findDocuments(DataSource ds, int id) throws Exception {
	//	Map<String, Object> parameters = new HashMap<>();
	//	parameters.put("pid", id);
	//	return super.search2(ds, "mc.miningconcession_finddocuments", parameters, new DocumentMapper());
	//}
	
	public List<ConservationAgreementModel> findBy2(DataSource ds, ConservationAgreementModel item) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ptext", item.getName());
		int srid = 0;
		byte[] geometryWkb = null;
		//if(item.getGeometry() != null) {
		//	WKBWriter wkb = new WKBWriter();
		//		geometryWkb = wkb.write(item.getGeometry());
		//		srid = item.getGeometry().getSRID();
		//}
		parameters.put("pgeometry", geometryWkb);
		parameters.put("psrid", srid);
		return null;
		//return super.search(ds, "mc.miningconcession_findby2", parameters, new EconomicActivityMapper());
	}
	
}
