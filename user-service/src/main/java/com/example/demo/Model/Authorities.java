package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Authorities {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	public String authority;
	
	@OneToOne(fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name = "username", nullable = false)
	public Users users;
	

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	

}
