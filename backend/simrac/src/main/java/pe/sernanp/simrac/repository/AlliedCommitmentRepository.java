package pe.sernanp.simrac.repository;

import java.util.Map;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import pe.sernanp.simrac.mapper.AlliedCommitmentMapper;
import pe.sernanp.simrac.model.AlliedCommitmentModel;

@Repository
public class AlliedCommitmentRepository extends BaseRepository<AlliedCommitmentModel>{
	
	
	@Override
	public AlliedCommitmentModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_compromisoaliado", id, new AlliedCommitmentMapper());
	}
	
	@Override
	public int insert(DataSource ds, AlliedCommitmentModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_compromisoaliado", item);
	}
	
	public int deleteDocument(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_insertar_compromisoaliado", id);
	}
	
	@Override
	protected void setParameters(Map<String, Object> parameters, AlliedCommitmentModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pagreementid", item.getConservationAgreement () .getId());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());
		 
	}
	
}
