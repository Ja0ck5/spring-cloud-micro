package com.ja0ck5.cloud.controlller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import com.ja0ck5.cloud.dao.UserDao;
import com.ja0ck5.cloud.entity.User;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class UserController {

	@Qualifier("eurekaClient")
	@Autowired
	EurekaClient eurekaClient;

	@Autowired
	DiscoveryClient discoveryClient;

	@Autowired
	UserDao userDao;

	@GetMapping("/simple/{id}")
	public User findById(@PathVariable Long id) {
		return userDao.findOne(id);
	}

	@GetMapping("/eureka/client/{id}")
	public InstanceInfo getEurekaClientInfo(@PathVariable Long id) {
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("SPRING-CLOUD-MICRO-PROVIDER-USER", false);
		return instanceInfo;
	}

	@GetMapping("/discover/client/{id}")
	public String getDiscoveryClientInfo(@PathVariable Long id) {
		ServiceInstance instance = discoveryClient.getLocalServiceInstance();
		return instance.getMetadata().toString();
	}

	@RequestMapping(value = "/user/{id}/{profile}", method = RequestMethod.GET)
	User findProfileById(@PathVariable("id") Long id,@MatrixVariable(value="q",pathVar = "id") int q, @MatrixVariable(pathVar = "profile") Map<String, String> complexParams){
		User user = new User();
		user.setId(id);
		user.setName(complexParams.get("name"));
		user.setUsername(complexParams.get("username"));
		user.setAge((short)q);
		return user;
	}

}
