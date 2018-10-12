package com.ja0ck5.cloud.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.ja0ck5.cloud.entity.User;
import com.ja0ck5.config.FeignConfiguration;

import feign.Param;
import feign.RequestLine;

@FeignClient(name = "spring-cloud-micro-provider-user", configuration = FeignConfiguration.class, /*fallback = HystrixClientFallback.class,*/fallbackFactory = HystrixClientFallbackFactory.class )
public interface UserFeignClient {

	/**
	 * 因为使用了 {@link FeignConfiguration},使用了 Feign 的默认注解，所以这里会启动失败
	 * 
	 * @param id
	 * @return
	 */
	// @RequestMapping(value = "/simple/{id}", method = RequestMethod.GET)
	// User findById(@PathVariable("id") Long id);

	@RequestLine("GET /simple/{id}")
	User findById(@Param("id") Long id);

}

@Component
class HystrixClientFallback implements UserFeignClient {

	@Override
	public User findById(Long id) {
		User user = new User();
		user.setId(-9999L);
		return user;
	}
}