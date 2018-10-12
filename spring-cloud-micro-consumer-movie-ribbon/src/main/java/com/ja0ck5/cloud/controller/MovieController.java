package com.ja0ck5.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ja0ck5.cloud.entity.User;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	LoadBalancerClient loadBalancerClient;

	/**
	 * use ribbon
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/user/ribbon/{id}")
	public User useRibbon(@PathVariable Long id) {
		ServiceInstance instance = this.loadBalancerClient.choose("spring-cloud-micro-provider-user");
		System.out.println(instance.getHost() + ":" + instance.getPort() + ":" + instance.getServiceId());
		// 使用 vip 访问
		return this.restTemplate.getForEntity("http://spring-cloud-micro-provider-user/simple/" + id, User.class)
				.getBody();
	}

	@GetMapping("/user/choose/{id}")
	public String chooseService(@PathVariable Long id) {
		ServiceInstance instance = this.loadBalancerClient.choose("spring-cloud-micro-provider-user");
		return instance.getHost() + ":" + instance.getPort() + ":" + instance.getServiceId();
	}

}
