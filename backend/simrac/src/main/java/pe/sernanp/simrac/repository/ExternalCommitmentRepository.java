package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import pe.sernanp.simrac.mapper.CommitmentMapper;
import pe.sernanp.simrac.mapper.ExternalCommitmentMapper;
import pe.sernanp.simrac.model.CommitmentModel;
import pe.sernanp.simrac.model.ExternalCommitmentModel;

@Repository
public class ExternalCommitmentRepository extends BaseRepository<ExternalCommitmentModel> {
	
	@Override
	protected void setParameters(Map<String, Object> parameters, ExternalCommitmentModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pagreementid", item.getConservationAgreement () .getId());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());
		 parameters.put("pobjetive", item.getObjetive());
		 parameters.put("pactionline", item.getActionLine());	 		 
	}
	
	@Override
	public ExternalCommitmentModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_compromiso_externo", id, new ExternalCommitmentMapper());
	}
	
	@Override
	public int insert(DataSource ds, ExternalCommitmentModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_compromiso_externo", item);
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_compromiso_externo", id);
	}
	
	public List<ExternalCommitmentModel> search(DataSource ds, int id) throws Exception {
		System.out.println(ds);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pagreementid",id);
		return super.search23(ds,"simrac.fn_buscar_compromiso_externo_acuerdo",parameters, new ExternalCommitmentMapper());
	}
		
}