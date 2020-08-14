package com.example.demo.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dao.OrderDao;
import com.example.demo.Dao.Tempdao;
import com.example.demo.Models.OrderFunds;
import com.example.demo.Models.Temp;
import com.example.demo.Response.Response;
import com.example.demo.Util.Util;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.jsonwebtoken.Claims;

@RequestMapping("/Order")
@RestController
public class OrderFundController {
	
	@Autowired
	public OrderDao O_dao;
	
	@Autowired
	public Tempdao T_dao;
	
	@Autowired 
	public Util util;

	
	
	@RequestMapping(value = "/Purchasefunds/{Id}", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "PurchasefundsFallback")
	public ResponseEntity<Response> Purchasefunds(@RequestHeader(name = "Authentication") String Token,
			@PathVariable int Id){
		
		Response response  = new Response();
		HttpStatus status = HttpStatus.CREATED;
		
		OrderFunds  O_F = new OrderFunds();
		Date date = new Date();
		
		try {
			Claims claims = util.CheckToken(Token);
			try {
			
				Temp temp = T_dao.findByusernameAndId(claims.get("SESS_USERNAME").toString(), Id);
				try {
					if(temp.getName()==null)
					{
						status = HttpStatus.BAD_REQUEST;
						response.setMgs("please add the policy first");
					}
						
				}
				catch(Exception e) {}
				
				try {
					if(temp.getAccountno()==0)
					{
						status = HttpStatus.BAD_REQUEST;
						response.setMgs("please select the Account");
					}
				}
				catch(Exception e) {}
				
				
				O_F.setAccountno(temp.getAccountno());
				O_F.setBankname(temp.getBankname());
				O_F.setName(temp.getName());
				O_F.setIfsc(temp.getIfsc());
				O_F.setMicr(temp.micr);
				O_F.setPan(temp.getPan());
				O_F.setAmount(temp.getAmount());
				O_F.setDate(date);
				
				O_dao.saveAndFlush(O_F);
				T_dao.deleteById(temp.getId());
				response.setMgs("Mutual Funds Bought successfylly");
				
			}
			catch(Exception e)
			{
				status = HttpStatus.NOT_FOUND;
				response.setMgs(" No Fund Found ");
			}
		}
		catch(Exception e)
		{
			status = HttpStatus.UNAUTHORIZED;
			response.setMgs(" Unauthorized ");
		}
			
		
		return new ResponseEntity<Response>(response, status);
	}

	
	
	@RequestMapping(value = "/Pan/{Id}", method = RequestMethod.GET)
	public ResponseEntity<Response> mutualfundsofuser (@RequestHeader(name = "Authentication") String Token,
			@PathVariable String Id){

		Response response  = new Response();
		HttpStatus status = HttpStatus.OK;
		try {
			List<OrderFunds> list = O_dao.findBypan(Id);
			if(list.isEmpty())
			{
				response.setMgs("No fund found, Buy your Fund Now");
				return new ResponseEntity<Response>(response, status);
			}
			
			response.setO_list(list);
		}
		
		catch(Exception e) 
		{
			response.setMgs(e.toString());
		}
		
		return new ResponseEntity<Response>(response, status);
	}
	
	
	
	@RequestMapping(value = "/{Pan}/{Id}", method = RequestMethod.GET)
	public ResponseEntity<Response> allmutualfundsofuser (@RequestHeader(name = "Authentication") String Token,
			@PathVariable String Pan, @PathVariable int Id){

		Response response  = new Response();
		HttpStatus status = HttpStatus.OK;
		try {
			OrderFunds orderFunds = O_dao.findBypanAndId(Pan, Id);
			if(orderFunds==null)
			{
				response.setMgs("No fund found");
				return new ResponseEntity<Response>(response, status);
			}
			
			response.setOrderFunds(orderFunds);
		}
		
		catch(Exception e) 
		{
			response.setMgs(e.toString());
		}
		
		return new ResponseEntity<Response>(response, status);
	}
	
//	 fallBack
	public ResponseEntity<Response> PurchasefundsFallback(@RequestHeader(name = "Authentication") String Token,
			@PathVariable int Id){
		Response response = new Response();
		response.setMgs(" Can't process the request , please try again later");
		return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
