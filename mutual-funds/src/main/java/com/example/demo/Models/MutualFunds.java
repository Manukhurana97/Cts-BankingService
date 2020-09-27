package com.example.demo.Models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MutualFunds {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	public String name;
	public String scheme_category;
	
	
	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getScheme_category() {
		return scheme_category;
	}


	public void setScheme_category(String scheme_category) {
		this.scheme_category = scheme_category;
	}


	
	
	
	
	

}
