package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;




@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
public class MutualFundsApplication {
	
	
	@Bean
	public WebClient.Builder getwebclientBuilder(){
		return WebClient.builder();
	}
	

	public static void main(String[] args) {
		SpringApplication.run(MutualFundsApplication.class, args);
	}

}
