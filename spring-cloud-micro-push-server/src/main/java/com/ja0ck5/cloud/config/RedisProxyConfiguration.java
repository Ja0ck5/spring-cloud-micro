package com.ja0ck5.cloud.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import redis.clients.jedis.RedisProxy;
import redis.clients.jedis.springboot.RedisProxyFactoryBean;

/**
 * Created by Jack on 2017/6/13.
 */
@Configuration
public class RedisProxyConfiguration {

	@Bean(name = "homeRedisProxyFactoryBeans", destroyMethod = "destroy")
	@Primary
	@ConfigurationProperties(prefix = "whale.redis.home")
	public RedisProxyFactoryBean primaryRedisProxyFactoryBeans() throws Exception {
		return new RedisProxyFactoryBean();
	}

	@Bean(name = "homeRedis")
	@Primary
	public RedisProxy homeRedis(@Qualifier("homeRedisProxyFactoryBeans") RedisProxyFactoryBean redisProxyFactoryBean)
			throws Exception {
		return redisProxyFactoryBean.build();
	}

	@Bean(name = "logRedisProxyFactoryBean")
	@ConfigurationProperties(prefix = "whale.redis.log")
	public RedisProxyFactoryBean logRedisProxyFactoryBean() throws Exception {
		return new RedisProxyFactoryBean();
	}

	@Bean(name = "logRedis")
	public RedisProxy logRedis(@Qualifier("logRedisProxyFactoryBean") RedisProxyFactoryBean logRedisProxyFactoryBean)
			throws Exception {
		return logRedisProxyFactoryBean.build();
	}

}
