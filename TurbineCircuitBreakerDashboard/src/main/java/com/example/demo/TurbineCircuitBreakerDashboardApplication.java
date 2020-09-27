package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableTurbine
@EnableEurekaClient
@SpringBootApplication
public class TurbineCircuitBreakerDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurbineCircuitBreakerDashboardApplication.class, args);
	}

}
