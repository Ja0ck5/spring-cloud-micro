package com.ja0ck5.cloud.service.support.guava.eventbus;

import org.springframework.util.Assert;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/**
	* @author sysu
	* @version 2017/7/13 10:15
	*/
public class EventBusSupport {

	private EventBus eventBus;
	private AsyncEventBus asyncEventBus;

	public EventBusSupport(final EventBus eventBus, final AsyncEventBus asyncEventBus) {
		Assert.notNull(eventBus, "EventBus should not be null");
		Assert.notNull(asyncEventBus, "AsyncEventBus should not be null");
		DeadEventListenerHandler deadEventListenerHandler = new DeadEventListenerHandler();
		this.eventBus = eventBus;
		this.asyncEventBus = asyncEventBus;
		this.eventBus.register(deadEventListenerHandler);
		this.asyncEventBus.register(deadEventListenerHandler);
	}

	public void post(final Object event) {
		this.eventBus.post(event);
	}

	public void postAsync(final Object event) {
		this.asyncEventBus.post(event);
	}
}
