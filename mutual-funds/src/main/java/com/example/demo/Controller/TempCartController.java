package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ApiCall.ApiCall;
import com.example.demo.Dao.FundsDao;
import com.example.demo.Dao.Tempdao;
import com.example.demo.Models.Account;

import com.example.demo.Models.MutualFunds;
import com.example.demo.Models.Temp;
import com.example.demo.Response.Response;
import com.example.demo.Util.Util;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.jsonwebtoken.Claims;
import javassist.NotFoundException;

@RestController
@RequestMapping("/Select")
public class TempCartController {

	@Autowired
	public FundsDao F_Dao;

	@Autowired
	public Tempdao T_dao;

	@Autowired
	public Util util;

	@Autowired
	public ApiCall apicall;

	@RequestMapping(value = "/MutualFund/{Id}", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "addFundInfoFallback")
	public ResponseEntity<Response> addFundInfo(@RequestHeader(name = "Authentication") String Token,
			@PathVariable int Id) {
		Response response = new Response();
		HttpStatus status = HttpStatus.CREATED;
		Temp temp = new Temp();

		try {

			Claims claims = util.CheckToken(Token);

			try {
				Optional<MutualFunds> funds = F_Dao.findById(Id);
				MutualFunds m_f = funds.get();

				try {
					Temp tmp = T_dao.findByusername(claims.get("SESS_USERNAME").toString());
					if (tmp != null) {
						T_dao.deleteById(tmp.getId());
					}
				} catch (Exception e) {
					System.out.println(e.toString());
				}

				temp.setUsername(claims.get("SESS_USERNAME").toString());
				temp.setName(m_f.getName());

				T_dao.saveAndFlush(temp);
				response.setMgs("Fund successfully added");

			} catch (Exception e) {
				status = HttpStatus.NOT_FOUND;
				response.setMgs("No MutualFund found");
			}

		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			response.setMgs("User is not login or invalid token");
		}
		return new ResponseEntity<Response>(response, status);
	}

	@HystrixCommand(fallbackMethod = "addAccountInfoFallback")
	@RequestMapping(value = "/Account/{AccNo}", method = RequestMethod.GET)
	public ResponseEntity<Response> addAccountinfo(@RequestHeader(name = "Authentication") String Token,
			@PathVariable int AccNo) {
		Response response = new Response();
		HttpStatus status = HttpStatus.CREATED;
		
		try {

			Claims claims = util.CheckToken(Token);
	
			try {
				Account acc = apicall.getAccount(AccNo);
				if (acc == null) {
					throw new NotFoundException("Account is invalid");
				}
				try {
	
					Temp temp = T_dao.findByusername(claims.get("SESS_USERNAME").toString());
	
					temp.setAccountno(acc.getAccountno());
					temp.setBankname(acc.getBankname());
					temp.setIfsc(acc.getIfsc());
					temp.setMicr(acc.getMicr());
					temp.setPan(acc.getPan());
					temp.setAmount(acc.getAmount());
					T_dao.saveAndFlush(temp);
					response.setMgs("Account successfully added");
	
				} catch (Exception e) {
					status = HttpStatus.NOT_FOUND;
					response.setMgs("Please select the policy first ");
				}
			} catch (Exception e) {
				status = HttpStatus.NOT_FOUND;
				response.setMgs("Account Number is invalid  ");
			}
		}
		catch(Exception e)
		{
			status = HttpStatus.UNAUTHORIZED;
			response.setMgs(" Unauthorized ");
		}
			
		return new ResponseEntity<Response>(response, status);
	}

	@RequestMapping(value = "/All", method = RequestMethod.GET)
	public ResponseEntity<Response> AlltempFund(@RequestHeader(name = "Authentication") String Token) {

		Response response = new Response();
		HttpStatus status = HttpStatus.CREATED;

		try {

			List<Temp> list = T_dao.findAll();

			if (list.isEmpty()) {
				status = HttpStatus.NOT_FOUND;
				response.setMgs("No data found");
			}

			response.setTemp_list(list);
		} catch (Exception e) {
			response.setMgs(e.toString());
		}

		return new ResponseEntity<Response>(response, status);

	}

//	fallback method
	public ResponseEntity<Response> addFundInfoFallback(@RequestHeader(name = "Authentication") String Token,
			@PathVariable int Id) {
		Response response = new Response();
		response.setMgs("Can't process request, Please try again later");
		return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<Response> addAccountInfoFallback(@RequestHeader(name = "Authentication") String Token,
			@PathVariable int Id) {
		Response response = new Response();
		response.setMgs("Can't process request, Please try again later");
		return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
