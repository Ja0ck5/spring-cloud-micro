package com.ja0ck5.cloud.controller;

import com.ja0ck5.cloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Value("${user.userService.path}")
	String userServicePath;

	@Autowired
	protected RestTemplate restTemplate;

	// @Autowired
	// @LoadBalanced // 使用 ribbon. 具有 ribbon 负载均衡
	// RestTemplate restTemplate;
	//
	@Autowired
	LoadBalancerClient loadBalancerClient;

	@GetMapping("/user/{id}")
	public User findById(@PathVariable Long id) {
		return this.restTemplate.getForObject(this.userServicePath + id, User.class);
	}

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

	// @GetMapping("/balanced/{id}")
	// public String useLoadBalanced(@PathVariable Long id) {
	// ServiceInstance serviceInstance =
	// this.loadBalancerClient.choose("micro-service-provider-user");
	// return serviceInstance.getHost()+" "+serviceInstance.getPort()+" "+
	// serviceInstance.getUri() +
	// serviceInstance.getServiceId()+""+serviceInstance.getMetadata();
	// }

}
