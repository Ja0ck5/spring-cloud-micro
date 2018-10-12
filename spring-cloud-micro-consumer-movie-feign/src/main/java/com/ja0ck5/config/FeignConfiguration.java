package com.ja0ck5.config;

import feign.Contract;
import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Jack on 2017/10/11.
 */
@Configuration
public class FeignConfiguration {

	/**
	 * This replaces the SpringMvcContract with feign.Contract.Default
	 * 
	 * @return
	 */
	@Bean
	public Contract feignContract() {
		return new feign.Contract.Default();
	}

	/**
	 * adds a RequestInterceptor to the collection of RequestInterceptor.
	 */
	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
		return new BasicAuthRequestInterceptor("ja0ck5", "jack123");
	}

	/**
	 * The Logger.Level object that you may configure per client
	 * @return
	 */
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

}
