package com.ja0ck5.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * 集群的监控
 */
@EnableTurbine
@SpringBootApplication
public class SpringCloudMicroTurbineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMicroTurbineApplication.class, args);
	}
}
