package com.ja0ck5.config;

import com.netflix.loadbalancer.RandomRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;

/**
 * 不能在主函数所扫描的包的路径下，不然会被所有的 @RibbonClients 分享
 */
@Configuration
public class UserRibbonConfiguration {

	@Autowired
	protected IClientConfig config;

	@Bean
	public IRule ribbonRule(IClientConfig config) {
		return new RandomRule();
	}

}