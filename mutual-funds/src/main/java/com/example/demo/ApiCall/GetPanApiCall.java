package com.example.demo.ApiCall;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("UserDetails")
public interface GetPanApiCall {
	@RequestMapping(value = "/GetPan", method = RequestMethod.GET)
	@LoadBalanced
	public String getPan(@RequestHeader(name = "Authentication") String Token);
}
