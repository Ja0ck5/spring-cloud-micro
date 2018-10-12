package com.ja0ck5.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

/**
 * 支持异构服务
 */
@SpringBootApplication
@EnableSidecar
public class SpringCloudMicroSidecarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMicroSidecarApplication.class, args);
	}
}
