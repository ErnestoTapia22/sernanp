package pe.sernanp.simrac.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer>{
				
	@Query(value="select u.* from sernanp.sistema as s "
			+ "	inner join sernanp.rol as r  on r.idsistema=s.idsistema "
			+ "	inner join sernanp.usuariorol as sr on sr.idrol = r.idrol "
			+ "	inner join sernanp.usuario as u on sr.idusuario=u.idusuario "
			+ "	inner join sernanp.persona as p on p.idpersona = u.idpersona "
			+ "	inner join sernanp.personanatural as per on per.idpersona = u.idpersona "
			+ "	where "
			+ "		per.nombre ilike %?1% and "
			+ "		u.usuario ilike %?2% and "
			+ "   	CONCAT(per.apepat,' ',per.apemat) ilike %?3% and "
			+ "  	s.idsistema=?4 and "
			+ "     case when ?5 > 0 then r.idrol = ?5 else 1 = 1 end", 
			nativeQuery=true)
	Page<UserModel> search(@Param("pname") String pname, @Param("pusername") String pusername, @Param("plastname") String plastname,
			@Param("psystem") int psystem, @Param("proleid") int proleid, Pageable page);
	
	@Query(value="select * from sernanp.usuario as u"
			+ "	inner join sernanp.personanatural as per on per.idpersona = u.idpersona "
			+ "	where per.numerodoc = :pdni and u.estado = 1 and u.idusuario not in ( "
			+ "			select u.idusuario from sernanp.sistema as s "
			+ "		inner join sernanp.rol as r  on r.idsistema=s.idsistema "
			+ "		inner join sernanp.usuariorol as sr on sr.idrol = r.idrol "
			+ "		inner join sernanp.usuario as u on sr.idusuario=u.idusuario "
			+ "		where s.idsistema=:psystem)", nativeQuery=true)
	List <UserModel> searchWithoutLogin(@Param("pdni") String pdni, @Param("psystem") int psystem);

	@Query(value="INSERT INTO sernanp.usuariorol(idrol, idusuario, int_estado, tsp_fecha_reg) VALUES (:proleid, :pid, 1, :pregistrationdate)", nativeQuery=true)
	void insert(@Param("proleid") int proleid, @Param("pid") int pid, @Param("pregistrationdate") Date pregistrationdate);
	
	//METODO PARA OBTENER UN TOKEN Y SETEARLO AL USUARIO
	/*@Query(value = "SELECT substr(encode(TOKEN, 'escape'), position('bearer' in encode(TOKEN, 'escape')) +9,\r\n"
			+ " LENGTH(encode(TOKEN, 'escape'))) as TOKEN\r\n"
			+ "	from oauth_access_token limit 1", nativeQuery=true)
	String getToken();*/

}