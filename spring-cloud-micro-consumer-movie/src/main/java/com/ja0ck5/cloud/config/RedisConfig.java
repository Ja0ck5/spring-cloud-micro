package com.ja0ck5.cloud.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	/**
	 * Type safe representation of application.properties
	 */
	@Autowired
	ClusterConfigurationProperties clusterProperties;

	@Autowired
	RedisConfigurationProperties redisProperties;

	public @Bean RedisConnectionFactory jedisConnectionFactory() {
		RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(clusterProperties.getNodes());
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(clusterConfiguration);
		jedisConnectionFactory.setPassword(redisProperties.getPassword());
		return jedisConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		// StringRedisTemplate的构造方法中默认设置了stringSerializer
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		// disable transaction
		template.setEnableTransactionSupport(false);
		// set key serializer
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);

		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		// set value serializer
		template.setDefaultSerializer(jackson2JsonRedisSerializer);

		template.setConnectionFactory(jedisConnectionFactory());
		template.afterPropertiesSet();
		return template;
	}

}