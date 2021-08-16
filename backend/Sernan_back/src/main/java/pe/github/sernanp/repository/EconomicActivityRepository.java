package pe.github.sernanp.repository;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.github.sernanp.entity.PaginatorEntity;
import pe.github.sernanp.mapper.EconomicActivityMapper;
import pe.github.sernanp.model.EconomicActivityModel;

@Repository
public class EconomicActivityRepository extends BaseRepository<EconomicActivityModel> {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	
	/*@Override
	public List<EconomicActivityModel> list(DataSource ds) throws Exception {
		List<EconomicActivityModel> items = new ArrayList<EconomicActivityModel>();
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			conn.setAutoCommit(false);
			CallableStatement proc = conn.prepareCall("{? = call simrac.fn_list_actividadeconomica() }");
			proc.registerOutParameter(1, Types.OTHER);
			proc.execute();
			EconomicActivityMapper mapper = new EconomicActivityMapper();
			ResultSet results = (ResultSet) proc.getObject(1);
			while (results.next())
			{
				EconomicActivityModel item = mapper.mapRow(results, 0);
				items.add(item);
			}
			results.close();
			proc.close();
		} catch (Exception e) {
		}		
		return items;
	}*/
	
	@Override
	public List<EconomicActivityModel> list(DataSource ds) throws Exception {
		return super.list(ds, "simrac.fn_listar_actividadeconomica", new EconomicActivityMapper());
	}
	
	public List<EconomicActivityModel> find(DataSource ds) throws Exception{
		System.out.println(ds);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pid",2);
		return super.search2(ds,"mc.miningconcession_search2",parameters, new EconomicActivityMapper());
	}
	
	@Override
	public List<EconomicActivityModel> search(DataSource ds, EconomicActivityModel item, PaginatorEntity paginator) throws Exception{
		System.out.println(item);
		return super.search(ds,"mc.miningconcession_search",item,paginator, new EconomicActivityMapper());
	}
	
	public List<EconomicActivityModel> findBy(DataSource ds, EconomicActivityModel item) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pcode", item.getCode());
		parameters.put("pname", item.getName());
		//parameters.put("pcertificatetitle", item.getCertificateTitle());
		parameters.put("pstate", item.getState());
		return super.search(ds, "mc.miningconcession_findby", parameters, new EconomicActivityMapper());
	}
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "mc.miningconcession_delete", id);
	}
	
	public int deleteDocument(DataSource ds, int id) throws Exception {
		return super.delete(ds, "mc.miningconcession_deletedocument", id);
	}
	@Override
	public int insert(DataSource ds, EconomicActivityModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_actividadeconomica", item);
	}

	@Override
	public int update(DataSource ds, EconomicActivityModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_actividadeconomica", item);
	}
	public int insertDocument(DataSource ds, int id, int documentId)
			throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pminingconcessionid", id);
		parameters.put("pdocumentid", documentId);
		return super.insert(ds, "mc.miningconcession_insertdocument", parameters);
	}
	@Override
	protected void setParameters(Map<String, Object> parameters, EconomicActivityModel item) throws IOException{
		 parameters.put("pid", item.getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());
	}
	public EconomicActivityModel detail(DataSource ds, int id) throws Exception {
		return super.detail(ds, "simrac.fn_detalle_actividadeconomica", id, new EconomicActivityMapper());
	}
	//public List<DocumentModel> findDocuments(DataSource ds, int id) throws Exception {
	//	Map<String, Object> parameters = new HashMap<>();
	//	parameters.put("pid", id);
	//	return super.search2(ds, "mc.miningconcession_finddocuments", parameters, new DocumentMapper());
	//}
	public List<EconomicActivityModel> findBy2(DataSource ds, EconomicActivityModel item) throws Exception {
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
		return super.search(ds, "mc.miningconcession_findby2", parameters, new EconomicActivityMapper());
	}
	
}
