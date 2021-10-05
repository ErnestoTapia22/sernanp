package pe.sernanp.simrac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.LoginModel;

public interface LoginRepository extends JpaRepository<LoginModel, Integer>{
		
	@Query(value="select * from sernanp.login where idlogin=173323", nativeQuery=true)
	LoginModel validate(@Param("code") String pcode);
	
}