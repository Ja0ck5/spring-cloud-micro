package com.ja0ck5.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ja0ck5.cloud.entity.User;
import com.ja0ck5.cloud.service.ServiceInfoFeignClient;
import com.ja0ck5.cloud.service.UserFeignClient;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	LoadBalancerClient loadBalancerClient;

	@Autowired
	protected UserFeignClient userFeignClient;

	@Autowired
	protected ServiceInfoFeignClient serviceInfoFeignClient;

	/**
	 * use ribbon
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/user/ribbon/{id}")
	public User useRibbon(@PathVariable Long id) {
		return this.restTemplate.getForObject("http://spring-cloud-micro-provider-user/simple/" + id, User.class);
	}

	@GetMapping("/user/feign/{id}")
	public User findById(@PathVariable Long id) {
		return this.userFeignClient.findById(id);
	}

	@GetMapping("/app/feign/{serviceName}")
	public String findById(@PathVariable String serviceName) {
		return this.serviceInfoFeignClient.getAppInfo(serviceName);
	}

}
