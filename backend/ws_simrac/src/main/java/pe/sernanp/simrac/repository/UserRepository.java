package pe.sernanp.simrac.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.sernanp.simrac.dto.AnpDTO;
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer>{
		
	@Query(value="select * from v_gdb_anp_plan where anp_codi ilike %?1% and anp_nomb ilike %?2%", nativeQuery=true)
	UserModel validate(@Param("code") String pcode);
	
	@Query(value="select * from v_gdb_anp_plan where anp_codi ilike %?1%", nativeQuery=true)
	Page<UserModel> search(@Param("code") String pcode, Pageable page);
	
	@Query(value="select * from v_gdb_anp_plan where anp_codi ilike %?1% and anp_nomb ilike %?2%", nativeQuery=true)
	List <UserModel> searchWithoutLogin(@Param("code") String pcode, @Param("code") int system);
}