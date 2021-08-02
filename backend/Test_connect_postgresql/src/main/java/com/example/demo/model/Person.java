package com.example.demo.model;

import org.locationtech.jts.geom.Geometry;

public class Person {
	private int Id;
	
	private String Name;
	private String LastName;
	private String Email;
	private int Age;
	private Geometry Geometry;
	

	public Person () {
		
	}
	
	public Person(int id, String nombre, String apellido, String correo) {
		this.Id = id;
		this.Name = nombre;
		this.LastName = apellido;
		this.Email = correo;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}
		
	public Geometry getGeometry() {
		return Geometry;
	}

	public void setGeometry(Geometry geometry) {
		Geometry = geometry;
	}
}
