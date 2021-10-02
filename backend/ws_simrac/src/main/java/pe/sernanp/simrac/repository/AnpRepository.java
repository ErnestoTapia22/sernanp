package pe.sernanp.simrac.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.sernanp.simrac.model.AnpModel;


public interface AnpRepository extends JpaRepository<AnpModel, Integer>{
		
	@Query(value="select ID, cast(NAME as text) ,cast(DESCRIPTION as text) from simrac.v_gdb_anp",nativeQuery=true)
	Page<AnpModel> findAll(String d, String b, Pageable page);
}