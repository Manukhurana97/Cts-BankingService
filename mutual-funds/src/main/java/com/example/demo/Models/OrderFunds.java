package com.example.demo.Models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderFunds {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	public String name;
	public Long accountno;
	public String ifsc;
	public String bankname;
	public String micr;
	public String pan;
	public float amount;
	public Date date;
	
	
	
	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Long getAccountno() {
		return accountno;
	}



	public void setAccountno(Long accountno) {
		this.accountno = accountno;
	}



	public String getIfsc() {
		return ifsc;
	}



	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}



	public String getBankname() {
		return bankname;
	}



	public void setBankname(String bankname) {
		this.bankname = bankname;
	}



	public String getMicr() {
		return micr;
	}



	public void setMicr(String micr) {
		this.micr = micr;
	}



	public String getPan() {
		return pan;
	}



	public void setPan(String pan) {
		this.pan = pan;
	}


	public float getAmount() {
		return amount;
	}


	public void setAmount(float amount) {
		this.amount = amount;
	}


	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}
	
	
	

}
