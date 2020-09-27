package com.example.demo.Response;

import java.util.List;

import com.example.demo.Model.Users;

public class Response {

	 
	 public String message;
	 public int status;
	 public String token;
	 public Users usr;
	 public List<Users> list;
	 
	 
	
	
	public List<Users> getList() {
		return list;
	}
	public void setList(List<Users> list) {
		this.list = list;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Users getUsr() {
		return usr;
	}
	public void setUsr(Users usr) {
		this.usr = usr;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

	 
	 
}

