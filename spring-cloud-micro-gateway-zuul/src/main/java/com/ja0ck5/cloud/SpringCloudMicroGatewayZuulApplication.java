package com.ja0ck5.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class SpringCloudMicroGatewayZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMicroGatewayZuulApplication.class, args);
	}
}
