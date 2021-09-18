package pe.sernanp.simrac.repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import pe.sernanp.simrac.mapper.WorkPlanMapper;
import pe.sernanp.simrac.model.WorkPlanModel;

@Repository
public class WorkPlanRepository extends BaseRepository<WorkPlanModel> {

	@Override
	protected void setParameters(Map<String, Object> parameters, WorkPlanModel item) throws Exception {
		 parameters.put("pid", item.getId2());
		 parameters.put("pagreementid", item.getConservationAgreement().getId2());
		 parameters.put("pname", item.getName());
		 parameters.put("pdescription", item.getDescription());
		 parameters.put("pregistrationdate", item.getRegistrationDate());
		 parameters.put("pstate", item.getState());
		 parameters.put("pyear", item.getYear());
		 parameters.put("pversion", item.getVersion());
		 parameters.put("pactive", item.getActive());
	}
	
	@Override
	public int insert(DataSource ds, WorkPlanModel item) throws Exception {
		return super.insert(ds, "simrac.fn_insertar_plantrabajo", item);
	}
	
	@Override
	public int delete(DataSource ds, int id) throws Exception {
		return super.delete(ds, "simrac.fn_delete_plan_trabajo", id);
	}
	
	public List<WorkPlanModel> searchByAgreement(DataSource ds, int agreementId) throws Exception {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("pagreementid", agreementId);
		return super.search23(ds,"simrac.fn_buscar_plantrabajoporacuerdo", parameters, new WorkPlanMapper());			
	}
}