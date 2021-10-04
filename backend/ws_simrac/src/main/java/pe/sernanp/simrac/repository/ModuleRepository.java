package pe.sernanp.simrac.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.sernanp.simrac.dto.AnpDTO;
import pe.sernanp.simrac.model.AnpModel;
import pe.sernanp.simrac.model.ModuleModel;


public interface ModuleRepository extends JpaRepository<ModuleModel, Integer>{
		
	
}