package pe.sernanp.simrac.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.sernanp.simrac.model.AnpModel;

public interface AnpRepository extends JpaRepository<AnpModel, Integer>{

	@Query(value="select ID,NAME,DESCRIPTION from simrac.v_gdb_anp",nativeQuery=true)
	List<AnpModel> findAll();
	
}