package pe.sernanp.simrac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.LoginModel;

public interface LoginRepository extends JpaRepository<LoginModel, Integer>{
		
	@Query(value="select idlogin,clave,usuario,27 as idsistema,idusuario,fecha "
			+ " from sernanp.login where clave = '1610987247778' ", nativeQuery=true)
	LoginModel validate(@Param("code") String pcode);
	
}