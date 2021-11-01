package pe.sernanp.simrac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.LoginModel;

public interface LoginRepository extends JpaRepository<LoginModel, Integer>{
		
	@Query(value="select idlogin,clave,usuario,27 as idsistema,idusuario,fecha "
			+ " from sernanp.login where clave = '1610987247778' ", nativeQuery=true)
	LoginModel validate(@Param("code") String pcode);
	
	//00d90a02cf20e493fbd19f2dffc703be
	//error al guardar a la tabla sistema = 26
	@Query(value="select * from sernanp.login where md5(clave)=:pcode", nativeQuery=true)
	LoginModel validate2(@Param("pcode") String pcode);
	
}