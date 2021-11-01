package pe.sernanp.simrac.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.AnpModel;

public interface AnpRepository extends JpaRepository<AnpModel, Integer>{
		
	@Query(value="select * from simrac.v_gdb_anp_plan where anp_codi ilike %?1% and anp_cate|| ' ' || anp_nomb ilike %?2% order by anp_cate,anp_nomb asc", nativeQuery=true)
	Page<AnpModel> findAll(@Param("code") String pcode, @Param("name")String pname, Pageable page);
	
	@Override
	@Query(value="select * from simrac.v_gdb_anp_plan order by anp_cate,anp_nomb asc", nativeQuery=true)
	List<AnpModel> findAll();
	
}