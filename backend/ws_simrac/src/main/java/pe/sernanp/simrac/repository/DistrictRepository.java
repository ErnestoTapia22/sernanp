package pe.sernanp.simrac.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.sernanp.simrac.model.DistrictModel;

public interface DistrictRepository extends JpaRepository<DistrictModel, Integer>{	
		
	@Query(value="select * from sernanp.ubigeo "
			+ " where coddist = '00' and codprov = '00'", nativeQuery=true)
	List<DistrictModel> list();
	
	@Query(value="select * from sernanp.ubigeo "
			+ "	where coddist = '00' and codprov <> '00' and coddpto = :pdepartment", nativeQuery=true)
	List<DistrictModel> searchByDepartment(@Param("pdepartment") String pcode);
	
	@Query(value="select * from sernanp.ubigeo "
			+ "					 where coddist <> '00' and coddpto || codprov = :pprovince", nativeQuery=true)
	List<DistrictModel> searchByProvince(@Param("pprovince") String pprovince);	
}