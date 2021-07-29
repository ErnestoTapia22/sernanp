package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;

@RestController
@RequestMapping(value = "/persona/")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = "saveQuery")
	public int saveQuery (@RequestBody Person persona)
    {
		int id = personService.Save(persona);
		return id; //new ResponseEntity(id, HttpStatus.OK);
    }
	
	@RequestMapping(value = "save")
	public int Save (@RequestBody Person person)
    {
		int id = personService.Save(person);
		return id; //new ResponseEntity(id, HttpStatus.OK);
    }
	
	@RequestMapping(value = "list")
	public java.util.List<Person> List()
    {
		java.util.List<Person> persons = personService.List();
		return persons; //new ResponseEntity(persons, HttpStatus.OK);
    }
}
