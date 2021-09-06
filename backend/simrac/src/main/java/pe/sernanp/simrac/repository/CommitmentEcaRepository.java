package pe.sernanp.simrac.repository;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.CommitmentEcaMapper;
import pe.sernanp.simrac.model.CommitmentEcaModel;

@Repository
public class CommitmentEcaRepository extends BaseRepository<CommitmentEcaModel> {
	
	@Override
	public CommitmentEcaModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_compromisoeca", id, new CommitmentEcaMapper());
	}
	
	@Override
	public int insert(DataSource ds, CommitmentEcaModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_compromisoeca", item);
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_compromisoeca", id);
	}
		
	@Override
	protected void setParameters(Map<String, Object> parameters, CommitmentEcaModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pagreementid", item.getConservationAgreement () .getId());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());
		 
	}
	
}
