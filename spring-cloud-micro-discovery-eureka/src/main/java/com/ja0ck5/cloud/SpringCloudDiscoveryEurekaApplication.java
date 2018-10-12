package com.ja0ck5.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringCloudDiscoveryEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudDiscoveryEurekaApplication.class, args);
	}
}
