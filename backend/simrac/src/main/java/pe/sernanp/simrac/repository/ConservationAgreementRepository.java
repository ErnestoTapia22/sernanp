package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import java.util.List;
//import org.locationtech.jts.io.WKBReader;
import org.springframework.stereotype.Repository;

import pe.gisriv.entity.PaginatorEntity;
import pe.sernanp.simrac.mapper.ConservationAgreementMapper;
import pe.sernanp.simrac.mapper.EconomicActivityMapper;
import pe.sernanp.simrac.model.ConservationAgreementModel;
import pe.sernanp.simrac.model.EconomicActivityModel;

@Repository
public class ConservationAgreementRepository extends BaseRepository<ConservationAgreementModel> {	
	
	@Override
	public List<ConservationAgreementModel> list(DataSource ds) throws Exception {
		return super.list2(ds, "simrac.fn_listar_acuerdoconservacion", new ConservationAgreementMapper());
	}
	
	public ConservationAgreementModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_acuerdoconservacion", id, new ConservationAgreementMapper());
	}
	
	@Override
	public List<ConservationAgreementModel> search(DataSource ds, ConservationAgreementModel item, PaginatorEntity paginator) throws Exception{		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pname", item.getName());
		return super.search2(ds,"simrac.fn_buscar_acuerdoconservacion",parameters,paginator, new ConservationAgreementMapper());
	}
	
	@Override
	public int insert(DataSource ds, ConservationAgreementModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_acuerdoconservacion", item);
	}

	@Override
	public int update(DataSource ds, ConservationAgreementModel item) throws Exception {
		return super.update(ds, "simrac.fn_actualizar_acuerdoconservacion", item);
	}
	
	@Override
	protected void setParameters(Map<String, Object> parameters, ConservationAgreementModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());
		 
		 parameters.put("pcode", item.getCode());
		 parameters.put("pvigency", item.getVigency());
		 parameters.put("pfirm", item.getFirm());
		 parameters.put("ppartmen", item.getPartMen());
		 parameters.put("ppartwomen", item.getPartWomen());
		 parameters.put("pbenperson", item.getBenPerson());
		 parameters.put("pbenindirect", item.getBenIndirect());
		 parameters.put("pnumfamily", item.getNumFamily());
		 parameters.put("pbenfamily", item.getBenFamily());
		 parameters.put("pareaambitc", item.getAreaAmbitc());
		 parameters.put("pproducedarea", item.getProducedArea());
		 parameters.put("pdetalleproduction", item.getDetalleProduction());
		 
		 
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
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_acuerdoconservacion", id);
	}
	
	
}
