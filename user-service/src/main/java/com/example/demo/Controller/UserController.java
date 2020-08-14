package com.example.demo.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dao.Userdao;
import com.example.demo.Model.GetUserdata;
import com.example.demo.Model.Users;
import com.example.demo.Response.Response;
import com.example.demo.Util.Util;
import com.example.demo.Validation.ValidateData;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.jsonwebtoken.Claims;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.userdetails.User;
import com.example.demo.Model.Login;


@RestController
public class UserController {

	@Autowired
	public JdbcUserDetailsManager jdbcUserDetailsManager;

	@Autowired
	public BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public AuthenticationManager authenticationManager;

	@Autowired
	public Userdao dao;

	@Autowired
	public Util util;
	
	 Logger logger = LoggerFactory.getLogger(UserController.class);
	
	 
//  special character matcher 	
	public boolean checkspecialcharacter(String data) 
	{
		Pattern special = Pattern.compile ("[!#$%&*()_+=|<>?{}\\[\\]~-]");
		
		Matcher value = special.matcher(data);
		if(value.find()) 
			return true;
		return false;
	}
	
//	email validation
	public boolean emailformatvalidation(String emaildata) 
	{
		String emailformat = "^[\\w-\\+]*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		
		Pattern pattern = Pattern.compile(emailformat);
		
		if(emaildata==null)
			return false;
		Matcher match = pattern.matcher(emaildata);
		
		return match.matches();
	}
	
	
	
//	pan card matcher
	public boolean panformatvalidation(String pancarddata) 
	{
		String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
		
		Pattern pattern  = Pattern.compile(regex);
		
		if(pancarddata==null)
			return false;
		
		Matcher matcher = pattern.matcher(pancarddata);
		
		return matcher.matches();
	}

	
	
// SignIn
	@PostMapping(value = "/Login")
	@HystrixCommand(fallbackMethod = "LoginFallback")
	public ResponseEntity<Response> LoginUser(@RequestBody Login login) {
		HttpStatus status = HttpStatus.OK;
		String token = null;
		Response response = new Response();
		try {
			
//			check for blank data			
			if(ValidateData.check(login.getEmail())) {
				status = HttpStatus.BAD_REQUEST;
				response.setMessage("Email can't be blank");
				return new ResponseEntity<Response>(response, status);
			}
			if(ValidateData.check(login.getPassword())) {
				status = HttpStatus.BAD_REQUEST;
				response.setMessage("Password can't be blank");
				
				return new ResponseEntity<Response>(response, status);
			}
			
//			email format validation
			if(!emailformatvalidation(login.getEmail()))
			{
				status = HttpStatus.BAD_REQUEST;
				response.setMessage("invalid emil format");
				
				return new ResponseEntity<Response>(response, status);
			}
			
			Authentication details;

			details = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
			String permission = details.getAuthorities().toString();
			if(permission.compareTo("[ROLE_ADMIN]")==0) 
				token = util.createTokenAdmin(details.getName());
			else 
				token = util.createToken(details.getName());
			
			
			response.setMessage("Login Success");
			response.setToken(token);
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			response.setMessage("Invalid Credential");
			logger.debug(e.toString());
		}

		return new ResponseEntity<Response>(response, status);
	}

	
	
// Register	
	@PostMapping(value = "/Signup")
	@HystrixCommand(fallbackMethod = "RegisterFallback")
	public ResponseEntity<Response> RegisterUser(@RequestBody GetUserdata myUser) {

		HttpStatus status = HttpStatus.CREATED;
		Response response = new Response();

		try {
			
//			check for blank data
			
			if(ValidateData.check(myUser.getEmail())) {
				status = HttpStatus.BAD_REQUEST;
				response.setMessage("Email can't be blank");
				return new ResponseEntity<Response>(response, status);
			}
			
			if(ValidateData.check(myUser.getPassword())) {
				status = HttpStatus.BAD_REQUEST;
				response.setMessage("Password can't be blank");
				return new ResponseEntity<Response>(response, status);
			}
			if(ValidateData.check(myUser.getPan())) {
				status = HttpStatus.BAD_REQUEST;
				response.setMessage("Pan can't be blank");
				return new ResponseEntity<Response>(response, status);
			}
			
			
//			check for Special characters
			if(checkspecialcharacter(myUser.getEmail())) {
				status = HttpStatus.NOT_IMPLEMENTED;
				response.setMessage("Email contain special characters");
				return new ResponseEntity<Response>(response, status);
				}
			
			if(checkspecialcharacter(myUser.getPan())) {
				status = HttpStatus.NOT_IMPLEMENTED;
				response.setMessage("Pan contain special characters");
				return new ResponseEntity<Response>(response, status);
				}
			
			if(checkspecialcharacter(myUser.getPassword())) {
				status = HttpStatus.NOT_IMPLEMENTED;
				response.setMessage("Password contain special characters");
				return new ResponseEntity<Response>(response, status);
				}
			
			
//			format validation
			if(!emailformatvalidation(myUser.getEmail()))
			{
				status = HttpStatus.BAD_REQUEST;
				response.setMessage("Invalid email format");
				
				return new ResponseEntity<Response>(response, status);
			}
//			
			if(!panformatvalidation(myUser.getPan()))
			{
				status = HttpStatus.BAD_REQUEST;
				response.setMessage("Invalid Pancard format");
				
				return new ResponseEntity<Response>(response, status);
			}
			
					
//			Check for duplicate data
			try {   
				Users users = dao.findByUsername(myUser.getEmail());
				
				if(users!=null)	{
					status = HttpStatus.CONFLICT;
					response.setMessage("Email is already registered with different account");
					return new ResponseEntity<Response>(response, status);}
			}
			catch(Exception e){
				logger.debug(e.toString());
				}
			
			
			try {  
				dao.findByPan(myUser.getPan());
				Users users_p = dao.findByPan(myUser.getPan());
				if(users_p!=null)	{
					status = HttpStatus.CONFLICT;
					response.setMessage("Pan is already registered with different account");
					return new ResponseEntity<Response>(response, status);}
			}
			catch(Exception ee) {
				logger.debug(ee.toString());
			}
				
			
//			password and confirm password match
			if(myUser.getPassword().compareTo(myUser.getConfirm_password())!=0) {
				status = HttpStatus.CONFLICT;
				response.setMessage("Password and Confirm password didn't match");
				return new ResponseEntity<Response>(response, status);}
		
		
//			saving username password & role
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(myUser.getRole()));
			String encodededPassword = passwordEncoder.encode(myUser.getPassword());
			User user = new User(myUser.getEmail(), encodededPassword, authorities);
			jdbcUserDetailsManager.createUser(user);

			
//			Saving additional data
			Users usr = dao.findByUsername(user.getUsername()); 
			usr.setContactNo(myUser.getContactNo());
			usr.setFirstname(myUser.getFirstname());
			usr.setLastname(myUser.getLastname());
			usr.setPan(myUser.getPan());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate date = LocalDate.parse(myUser.getDob(), formatter);
			usr.setDob(date);
			dao.saveAndFlush(usr);

			response.setMessage("User Register Successfully");
		
			
		} catch (Exception e) {
			status = HttpStatus.CONFLICT;
			response.setMessage("Invalid email format ");
			logger.debug(e.toString());
		}

		return new ResponseEntity<Response>(response, status);

	}
	
	@PostMapping("/AllUserInfomation")
//	@HystrixCommand(fallbackMethod = "AllRecordsFallback")
	public ResponseEntity<Response> Allrecords(@RequestHeader(name = "Authentication") String token){
		HttpStatus status = HttpStatus.CREATED;
		Response response = new Response();
		
			try {
				
				List<Users> lst = dao.findAll();
				if (lst.isEmpty())
				{
					status = HttpStatus.NO_CONTENT;
					response.setMessage("No data found");
					return new ResponseEntity<Response>(response, status);
				}
				status = HttpStatus.OK;
				response.setMessage("success");
				response.setList(lst);
				
			}
			catch(Exception e)
			{
				status = HttpStatus.METHOD_NOT_ALLOWED;
				response.setMessage("Not Authotized");
				logger.debug(e.toString());
			}
			
		
		return new ResponseEntity<Response>(response, status);
	}

	
	
//	Gatting data from token
	@GetMapping("/Tokeninfo")
	public Users GetUserinfoFromtoken(@RequestHeader(name = "Authentication") String Token) {
		try {
			Claims claims = util.CheckToken(Token);
			
			if(util.isTokenExpired(Token))
				throw new BadCredentialsException("Token is expired");
			System.out.println("Claims:  "+dao.findByUsername(claims.get("SESS_USERNAME").toString()));
			return dao.findByUsername(claims.get("SESS_USERNAME").toString());
		}
		catch(Exception e)
		{
			logger.debug(e.toString());
			return null;
		}
	}
	
	
//	Getting pan from token
	@GetMapping("/GetPan")
	public String getPan(@RequestHeader(name = "Authentication") String Token) {
		try {
			Claims claims = util.CheckToken(Token);
			
			if(util.isTokenExpired(Token))
				throw new BadCredentialsException("Token is expired");
			System.out.println("Claims:  "+dao.findByUsername(claims.get("SESS_USERNAME").toString()));
			 Users user = dao.findByUsername(claims.get("SESS_USERNAME").toString());
			 return user.getPan();
		}
		catch(Exception e)
		{
			logger.debug(e.toString());
			return null;
		}
		
	}
	
	
	
	
// Fallback Method	 
	public ResponseEntity<Response> LoginFallback(@RequestBody Login login) {
	        Response response = new Response();
	        response.setMessage("Can't connect  Right Now ");
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
		 
	public ResponseEntity<Response> RegisterFallback(@RequestBody GetUserdata myUser) {
		        Response response = new Response();
		        response.setMessage("Can't connect  Right Now ");
		        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	public ResponseEntity<Response> AllRecordsFallback(@RequestHeader(name = "Authentication") String token){
		 Response response = new Response();
	        response.setMessage("Can't connect  Right Now ");
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	
}
