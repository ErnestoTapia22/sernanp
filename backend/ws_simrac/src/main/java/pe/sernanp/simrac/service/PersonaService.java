package pe.sernanp.simrac.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.simrac.model.PersonaModel;
import pe.sernanp.simrac.repository.PersonaRepository;

@Service
public class PersonaService {
	
	@Autowired
	private PersonaRepository personaRepository;
	
	public PersonaModel create (PersonaModel persona) {
		return personaRepository.save(persona);
	}
	
	public List<PersonaModel> getAllPersonas () {
		return personaRepository.findAll();
	}
	public void delete (PersonaModel persona) {
		personaRepository.delete(persona);
	}
	public Optional<PersonaModel> findById (Long id) {
		return personaRepository.findById(id);
	}	
}