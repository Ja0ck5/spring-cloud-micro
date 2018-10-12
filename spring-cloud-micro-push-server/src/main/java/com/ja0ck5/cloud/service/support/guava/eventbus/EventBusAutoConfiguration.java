package com.ja0ck5.cloud.service.support.guava.eventbus;

import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/**
	* @author sysu
	* @version 2017/7/13 10:10
	*/
@Configuration
public class EventBusAutoConfiguration {

	@Bean
	public EventBusSupport eventBusWrapper() {
		return new EventBusSupport(eventBus(), asyncEventBus());
	}

	@Bean
	public EventBus eventBus() {
		EventBus eventBus = new EventBus();
		return eventBus;
	}

	@Bean
	public AsyncEventBus asyncEventBus() {
		AsyncEventBus asyncEventBus = new AsyncEventBus("asyncDefault", Executors.newFixedThreadPool(30));
		return asyncEventBus;
	}

	@Bean
	public EventBusSubscriberBeanPostProcessor subscriberAnnotationProcessor() {
		return new EventBusSubscriberBeanPostProcessor(eventBus(), asyncEventBus());
	}
}
