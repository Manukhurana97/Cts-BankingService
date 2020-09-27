package com.example.demo.ApiCall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.Models.Account;

@Service
public class ApiCall {

	@Autowired
	private WebClient.Builder webBClientBuilder;
	
	
	public Account getAccount(int AccNo) {
		System.out.println(AccNo);
		return webBClientBuilder.build()
				.get()
				.uri("http://localhost:8002/AccountNo/"+AccNo)
				.retrieve().bodyToMono(Account.class)
				.block();
	}

}
