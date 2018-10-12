package com.ja0ck5.cloud.service;

import com.ja0ck5.cloud.entity.User;
import com.ja0ck5.cloud.service.i.UserFeignHystrixClientWithFallBackFactory;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Jack on 2017/10/16.
 */
@Component
public class HystrixClientFallbackFactory implements FallbackFactory<UserFeignClient> {

	public static final Logger LOGGER = LoggerFactory.getLogger(HystrixClientFallbackFactory.class);

	@Override
	public UserFeignClient create(Throwable throwable) {
		LOGGER.info("fallback; reason was:{} ", throwable.getMessage());
		return (UserFeignHystrixClientWithFallBackFactory) id -> {
			User user = new User();
			user.setId(id + -9999);
			return user;
		};
	}
}
