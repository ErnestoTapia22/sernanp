package pe.sernanp.simrac.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.ConservationAgreementModel;

public interface ConservationAgreementRepository extends JpaRepository<ConservationAgreementModel, Integer> {

	@Query(value="select t_ac.* FROM simrac.t_acuerdo_conservacion t_ac "
			+ "	INNER JOIN simrac.t_estado_acuerdo as t_ea ON t_ea.srl_id = t_ac.int_estadoacuerdoid "
			+ "	inner join simrac.v_gdb_anp as anp on anp.anp_id = t_ac.int_anpid "
			+ "	left join simrac.t_fuente as so on so.srl_id = t_ac.int_fuenteid "
			+ "	left join sernanp.ubigeo as d on d.coddpto ||d.codprov || d.coddist = t_ac.var_distritoid "
			+ "	where t_ac.var_cod ilike %?1% "
			+ "	and t_ac.var_nom ilike %?2% "
			+ "	and case when ?3 > 0 then t_ac.int_anpid = ?3 else 1 = 1 end "
			+ "	and case when ?4 > 0 then t_ac.int_estadoacuerdoid = ?4 else 1 = 1 end "
			+ "	and case when ?5 <> '' then SUBSTRING(t_ac.var_distritoid,1,2) = ?5 else 1 = 1 end "	
			+ "	and case when ?6 <> '' then SUBSTRING(t_ac.var_distritoid,1,4) = ?6 else 1 = 1 end "
			+ "	and case when ?7 <> '' then t_ac.var_distritoid = ?7 else 1 = 1 end "
			+ "	and case when ?10 > 0 then t_ac.dte_fec_firma >= ?8 else 1 = 1 end "
			+ "	and case when ?10 > 0 then t_ac.dte_fec_firma <= ?9 else 1 = 1 end ",
			//+ "			  and case when pstate = false then 1 = 1 /*t_ac.bol_flg = pstate*/ else 1 = 1 end;",
			nativeQuery=true)
	Page<ConservationAgreementModel> search(String code, String name, int anpId, int agreementStateId,
											String departmentId, String provinceId, String districtId,
											Date firm, Date firmEnd, int id, Pageable page);
	
	@Query(value="select t_ac.* FROM simrac.t_acuerdo_conservacion t_ac "
			+ "	INNER JOIN simrac.t_estado_acuerdo as t_ea ON t_ea.srl_id = t_ac.int_estadoacuerdoid "
			+ "	inner join simrac.v_gdb_anp as anp on anp.anp_id = t_ac.int_anpid "
			+ "	left join simrac.t_fuente as so on so.srl_id = t_ac.int_fuenteid "
			+ "	left join sernanp.ubigeo as d on d.coddpto ||d.codprov || d.coddist = t_ac.var_distritoid "
			+ "	where t_ac.var_cod ilike %?1% "
			+ "	and t_ac.var_nom ilike %?2% "
			+ "	and case when ?3 > 0 then t_ac.int_anpid = ?3 else 1 = 1 end "
			+ "	and case when ?4 > 0 then t_ac.int_estadoacuerdoid = ?4 else 1 = 1 end "
			+ "	and case when ?5 <> '' then SUBSTRING(t_ac.var_distritoid,1,2) = ?5 else 1 = 1 end "	
			+ "	and case when ?6 <> '' then SUBSTRING(t_ac.var_distritoid,1,4) = ?6 else 1 = 1 end "
			+ "	and case when ?7 <> '' then t_ac.var_distritoid = ?7 else 1 = 1 end "
			+ "	and case when ?10 > 0 then t_ac.dte_fec_firma >= ?8 else 1 = 1 end "
			+ "	and case when ?10 > 0 then t_ac.dte_fec_firma <= ?9 else 1 = 1 end ",
			//+ "			  and case when pstate = false then 1 = 1 /*t_ac.bol_flg = pstate*/ else 1 = 1 end;",
			nativeQuery=true)
	List<ConservationAgreementModel> search(String code, String name, int anpId, int agreementStateId,
											String departmentId, String provinceId, String districtId,
											Date firm, Date firmEnd, int id);
	
	@Query(value="select t_ac.*, SUBSTRING(t_ac.var_distritoid,1,2) FROM simrac.t_acuerdo_conservacion t_ac "
			+ "		where SUBSTRING(t_ac.var_distritoid,1,2) = :pdepartment" ,
			nativeQuery=true)
	List<ConservationAgreementModel> searchByDepartment(@Param("pdepartment") String pdepartment);
	
}