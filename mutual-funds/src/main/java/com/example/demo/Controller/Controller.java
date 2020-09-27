package com.example.demo.Controller;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dao.FundsDao;
import com.example.demo.Models.Funddata;
import com.example.demo.Models.MutualFunds;
import com.example.demo.Response.Response;
import com.example.demo.datavalidation.ValidateData;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Funds")
public class Controller {

	@Autowired
	public FundsDao dao;

	@PostMapping("/Create-Funds")
	@HystrixCommand(fallbackMethod = "createMutualFundsFallback")
	public ResponseEntity<Response> createFunds(@RequestHeader(name = "Authentication") String token,
			@RequestBody Funddata data) {
		Response response = new Response();
		HttpStatus status = HttpStatus.CREATED;
		MutualFunds fund = new MutualFunds();

		try {
//			checking for blank data
			if (ValidateData.check(data.getFundId())) {
				status = HttpStatus.BAD_REQUEST;
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.setMgs("MutualFund Name can't be blank");
				return new ResponseEntity<Response>(response, status);
			}

			fund.setName((data.getFundId()));	
			fund.setScheme_category(data.getScheme_category());

			dao.saveAndFlush(fund);
			response.setStatus(HttpStatus.CREATED.value());
			response.setMgs("MutualFund Successfully created");

		} catch (Exception e) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setMgs("Amount cant be null ");
		}

		return new ResponseEntity<Response>(response, status);
	}

	@GetMapping("/All-Funds")
	public ResponseEntity<Response> getAllFunds(@RequestHeader(name = "Authentication") String token) {

		Response response = new Response();
		HttpStatus status = HttpStatus.OK;
		try {
			List<MutualFunds> list = dao.findAll();
			if (list.isEmpty()) {
				response.setMgs("No mutual Funds");
			} else {
				response.setList(list);
			}

		} catch (Exception e) {
		}

		return new ResponseEntity<Response>(response, status);
	}

	@RequestMapping(value = "/Fund/{Id}", method = RequestMethod.GET)
	public ResponseEntity<Response> getFundbyId(@RequestHeader(name = "Authentication") String token,
			@PathVariable int Id) {

		Response response = new Response();
		HttpStatus status = HttpStatus.OK;
		try {
			Optional<MutualFunds> mutualFund = dao.findById(Id);
			if (mutualFund == null) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.setMgs("No mutual Funds");
			} else {
				response.setByid(mutualFund);
			}

		} catch (Exception e) {
		}

		return new ResponseEntity<Response>(response, status);
	}

//		fallback Method
	public ResponseEntity<Response> createMutualFundsFallback(@RequestHeader(name = "Authentication") String token,
			@RequestBody Funddata data) {
		Response response = new Response();
		response.setMgs("Can't process the operation right now");

		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);

	}

}
