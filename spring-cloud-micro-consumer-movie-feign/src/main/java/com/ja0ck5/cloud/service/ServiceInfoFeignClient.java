package com.ja0ck5.cloud.service;

import com.ja0ck5.config.ServiceInfoFeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 单个客户端 禁用 Hystrix
 * Created by Jack on 2017/10/11.
 */
@FeignClient(name = "spring-cloud-service-info", url = "http://localhost:8761/", configuration = ServiceInfoFeignConfiguration.class)
public interface ServiceInfoFeignClient {

	@RequestMapping(value = "/eureka/apps/{serviceName}")
	String getAppInfo(@PathVariable("serviceName") String serviceName);

}
