package com.ja0ck5.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringCloudMicroProviderUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMicroProviderUserApplication.class, args);
	}
}
