package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personaRepository;
	
	public int SaveQuery(Person persona) {
		return personaRepository.InsertQuery(persona);
	}
	
	public int Save(Person person) {
		
		int id2 = 0;
		
		if (person.getId() == 0)
			id2 = personaRepository.Insert(person);
		else
			id2 = personaRepository.Update(person);
		
		return id2;
	}
	
	public java.util.List<Person> List() {
		return personaRepository.List();
	} 
}
