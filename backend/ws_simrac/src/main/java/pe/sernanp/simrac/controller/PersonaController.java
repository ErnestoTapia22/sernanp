package pe.sernanp.simrac.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.sernanp.simrac.model.PersonaModel;
import pe.sernanp.simrac.service.PersonaService;

@RestController
@RequestMapping ("/api/persona")
public class PersonaController {
		
	@Autowired
	private PersonaService personaService;
	
	@PostMapping
	private ResponseEntity<PersonaModel> guardar (@RequestBody PersonaModel persona) {
		PersonaModel temporal = personaService.create(persona);
		
		try {
			return ResponseEntity.created(new URI("/api/persona" + temporal.getId())).body (temporal);
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}	
	
	@GetMapping
	private ResponseEntity<List<PersonaModel>> listarTodasLasPersonas () {
		return ResponseEntity.ok(personaService.getAllPersonas());	
	}
	
	@DeleteMapping
	private ResponseEntity<Void> eliminarPersona (@RequestBody PersonaModel persona) {
		personaService.delete(persona);
		return ResponseEntity.ok().build();
	}
		
	@GetMapping (value = "{id}")
	private ResponseEntity<Optional<PersonaModel>> listarPersonasPorId (@PathVariable ("id") Long id) {
		return ResponseEntity.ok(personaService.findById(id));
	}
		
}