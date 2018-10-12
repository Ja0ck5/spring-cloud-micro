package com.ja0ck5.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.ja0ck5.cloud.config.UserRibbonConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
// 可以使用 配置文件配置 ribbon
@RibbonClient(name = "spring-cloud-micro-provider-user", configuration = UserRibbonConfiguration.class)
@ComponentScan(excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeFromComponentScan.class) })
@EnableCircuitBreaker
public class SpringCloudMicroConsumerMovieHystrixApplication {

	/**
	 * 没有负载平衡的RestTemplate480 --- [trap-executor-0] c.n.d.s.r.aws.ConfigClusterResolver      : Resolving eureka endpoints via configuration
	 2017-10-10 16:45:34.774  INFO 13480 --- [trap-executor-0] c.n.d.s.r.aws.ConfigClusterResolver      : Resolving eureka endpoints via configuration
	 *
	 * @return
	 * @Primary 注释，以消除不合格的@Autowired注入
	 */
	@Primary
	@Bean
	@LoadBalanced
	// 加了这个注解才能解决 更具服务名称发现服务。http://SPRING-CLOUD-MICRO-PROVIDER-USER/
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMicroConsumerMovieHystrixApplication.class, args);
	}
}
