package pe.sernanp.simrac.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.MonitoringModel;

public interface MonitoringRepository extends JpaRepository<MonitoringModel, Integer> {

	@Query(value="select distinct m.* from simrac.t_actividad as a "
			+ "	inner join simrac.t_respuesta as r on r.int_actividadid = a.srl_id "
			+ "	inner join simrac.t_monitoreo as m on m.srl_id = r.int_monitoreoid "
			+ "	inner join simrac.t_plan_trabajo as pt on pt.srl_id = a.int_plantrabajoid "
			+ "	where pt.int_acuerdoid=:pagreementid ", nativeQuery=true)
    List<MonitoringModel> searchByMonitoringAndAgreement(@Param("pagreementid") int id);
}