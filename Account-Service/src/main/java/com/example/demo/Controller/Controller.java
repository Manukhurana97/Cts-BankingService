package com.example.demo.Controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ApiCall.Apicall;
import com.example.demo.Dao.Dao;
import com.example.demo.Model.Account;
import com.example.demo.Model.AccountIncommingdata;

import com.example.demo.Response.Response;
import com.example.demo.Validation.ValidateData;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@CrossOrigin(origins = "http://localhost:4200")
@RestController	
public class Controller {

	@Autowired
	public Dao Acc_dao;

	@Autowired
	private Apicall call;
	
	

	
//	pattern matcher
	public boolean checkspecialcharacter(String data) {
		Pattern special = Pattern.compile("[!#$%&*()_+=|<>?{}\\[\\]~-]");

		Matcher value = special.matcher(data);
		if (value.find())
			return true;
		return false;
	}



	@PostMapping("/Add-Account")
	@HystrixCommand(fallbackMethod = "CreateAccountFallback")
	public ResponseEntity<Response> CreateAccount(@RequestHeader(name = "Authentication") String token,
			@RequestBody AccountIncommingdata data) {

		Response response = new Response();
		HttpStatus status = HttpStatus.OK;
		DecimalFormat D_F = new DecimalFormat("0.00");
		
			Account acc = new Account();

//			 check for blank data
			if (ValidateData.check(data.getBankname())) {
				status = HttpStatus.BAD_REQUEST;
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.setMessage("Bank Name can't be blank");
				return new ResponseEntity<Response>(response, status);
			}

			if (ValidateData.check(data.getIfsc())) {
				status = HttpStatus.BAD_REQUEST;
				response.setStatus(HttpStatus.BAD_GATEWAY.value());
				response.setMessage("Ifsc can't be blank");
				return new ResponseEntity<Response>(response, status);
			}
			
			if(data.getAmount()<=0 ) 
			{
				status = HttpStatus.BAD_REQUEST;
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.setMessage("Amount cant be 0");
				return new ResponseEntity<Response>(response, status);
			}
			
//			 check for special character
				if (checkspecialcharacter(data.getBankname())) {
					status = HttpStatus.BAD_REQUEST;
					response.setStatus(HttpStatus.BAD_REQUEST.value());
					response.setMessage("Bank Name Contain Special characters");
					return new ResponseEntity<Response>(response, status);
				}

				if (checkspecialcharacter(data.getIfsc())) {
					status = HttpStatus.BAD_REQUEST;
					response.setStatus(HttpStatus.BAD_REQUEST.value());
					response.setMessage("Ifcs code Contain Special characters");
					return new ResponseEntity<Response>(response, status);
				}
			
				
// 			Account No validation 
			try {
				Account accnt = Acc_dao.findByaccountno(data.getAccountno());
				if (accnt != null) {
					status = HttpStatus.CONFLICT;
					response.setStatus(HttpStatus.CONFLICT.value());
					response.setMessage("Account already associated with different users");
					return new ResponseEntity<Response>(response, status);
				}
			} catch (Exception e) {
				System.out.println("unique account no");
			}

			try {
				String len = String.valueOf(data.getAccountno());
				if(len.length()!=10)
				{
					status = HttpStatus.UNPROCESSABLE_ENTITY;
					response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
					response.setMessage("Account no is invalid, Please check and try again");
					return new ResponseEntity<Response>(response, status);
				}
			}
			catch(Exception e) {}
			
			try {
			
	// 			Api calling
				String pan = call.getPan(token);
				if (pan.isEmpty()) {
					status = HttpStatus.UNAUTHORIZED;
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.setMessage("User is unauthenticated");
					return new ResponseEntity<Response>(response, status);
				}
	
	
	
				D_F.setRoundingMode(RoundingMode.UP);
				float amount=Float.parseFloat(D_F.format((data.getAmount()*1.00)));
				
				acc.setAccountno(data.getAccountno());
				acc.setBankname(data.getBankname());
				acc.setIfsc(data.getIfsc());
				acc.setMicr(data.getMicr());
				acc.setAmount(amount);
				acc.setPan(pan);
					
				Acc_dao.saveAndFlush(acc);
				response.setStatus(HttpStatus.CREATED.value());
				response.setMessage("Account created successfully");
			}
			catch(Exception e) 
			{
				status = HttpStatus.UNAUTHORIZED;
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setMessage("Invalid User");
			}
		

		return new ResponseEntity<Response>(response, status);
	}

//	
	@HystrixCommand(fallbackMethod = "GetAllUserAccount")
	@GetMapping("/AllAccount")
	public ResponseEntity<Response> getbypanid(@RequestHeader(name = "Authentication") String token) {
		Response response = new Response();
		HttpStatus status = HttpStatus.OK;
		try {
			
			String pan = call.getPan(token);
			if (pan.isEmpty()) {
				status = HttpStatus.UNAUTHORIZED;
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setMessage("User is unauthenticated");
				return new ResponseEntity<Response>(response, status);
			}


			
			List<Account> list = Acc_dao.findByPan(pan);
			if (list.isEmpty()) {
				response.setStatus(HttpStatus.OK.value());
				status = HttpStatus.BAD_REQUEST;
				response.setMessage("On data found");

			} else {
				
				response.setStatus(HttpStatus.OK.value());
				response.setLst(list);
			}

		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setMessage("error");
			System.out.println(e);
		}
		return new ResponseEntity<Response>(response, status);

	}
	
//
	@GetMapping("/AccountNo/{AccNo}")
	public Account getbyAccountNo(@PathVariable long AccNo) {
		Account acc = null;
		try {

			acc = Acc_dao.findByaccountno(AccNo);

		} catch (Exception e) {
			System.out.println(e);
		}
		return acc;

	}
	
//	fallback method 
	public ResponseEntity<Response> CreateAccountFallback(@RequestHeader(name = "Authentication") String token,
			@RequestBody AccountIncommingdata data) {
		Response response = new Response();
        response.setMessage("Can't connect  Right Now ");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<Response> GetByPanIdFallback(@RequestHeader(name = "Authentication") String token,
			@PathVariable String pan) {
		Response response = new Response();
        response.setMessage("Can't connect  Right Now ");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<Response> GetAllUserAccount(@RequestHeader(name = "Authentication") String token) {
		Response response = new Response();
        response.setMessage("Can't connect  Right Now ");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
