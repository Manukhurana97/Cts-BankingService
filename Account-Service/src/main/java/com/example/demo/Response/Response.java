package com.example.demo.Response;

import java.util.List;

import com.example.demo.Model.Account;

public class Response {
	
	public String message;
	public List<Account> lst;
	public int status;

	
	
	public List<Account> getLst() {
		return lst;
	}

	public void setLst(List<Account> lst) {
		this.lst = lst;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	
	

}
