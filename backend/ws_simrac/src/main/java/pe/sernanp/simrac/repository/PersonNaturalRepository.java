package pe.sernanp.simrac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.PersonNaturalModel;

public interface PersonNaturalRepository extends JpaRepository<PersonNaturalModel, Integer>{
				
	@Query(value="select * from sernanp.personanatural where idpersona =:pcode", nativeQuery=true)
	PersonNaturalModel search(@Param("pcode") int pcode);

}