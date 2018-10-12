package com.ja0ck5.cloud;

import com.ja0ck5.cloud.filter.PreCustomZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class SpringCloudMicroGatewayZuulFilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMicroGatewayZuulFilterApplication.class, args);
	}

	@Bean
	public PreCustomZuulFilter preCustomZuulFilter() {
		return new PreCustomZuulFilter();
	}
}
