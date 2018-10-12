package com.ja0ck5.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class SpringCloudMicroConsumerMovieApplication {

	/**
	 * 没有负载平衡的RestTemplate
	 *
	 * @return
	 * @Primary 注释，以消除不合格的@Autowired注入
	 */
	@Primary
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMicroConsumerMovieApplication.class, args);
	}
}
