package pe.github.sernan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.github.sernan.model.EconomicActivity;
import pe.github.sernan.repository.EconomicActivityRepository;

@Service
public class EconomicActivityService {

	
	@Autowired
	private EconomicActivityRepository economicActivityRepository;
	
	public java.util.List<EconomicActivity> List() {
		return economicActivityRepository.List();
	} 
	
}
