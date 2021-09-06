package pe.sernanp.simrac.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import pe.sernanp.simrac.mapper.AlliedMapper;
import pe.sernanp.simrac.mapper.CommitmentMapper;
import pe.sernanp.simrac.model.AlliedModel;
import pe.sernanp.simrac.model.CommitmentModel;

@Repository
public class CommitmentRepository extends BaseRepository<CommitmentModel> {
	
	@Override
	public CommitmentModel detail(DataSource ds, int id) throws Exception {
		return super.detail2(ds, "simrac.fn_detalle_compromiso", id, new CommitmentMapper());
	}
	
	@Override
	public int insert(DataSource ds, CommitmentModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_compromiso", item);
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_eliminar_compromiso", id);
	}
	
	
	@Override
	protected void setParameters(Map<String, Object> parameters, CommitmentModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pagreementid", item.getConservationAgreement () .getId());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());
		 
	}
	
	public List<CommitmentModel> search(DataSource ds, int id) throws Exception {
		System.out.println(ds);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pagreementid",id);
		return super.search23(ds,"simrac.fn_buscar_compromisoporacuerdo",parameters, new CommitmentMapper());
		
	}
	
}
