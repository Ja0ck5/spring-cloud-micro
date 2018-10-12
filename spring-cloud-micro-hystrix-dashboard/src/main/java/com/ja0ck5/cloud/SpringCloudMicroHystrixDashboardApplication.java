package com.ja0ck5.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
public class SpringCloudMicroHystrixDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMicroHystrixDashboardApplication.class, args);
	}
}
