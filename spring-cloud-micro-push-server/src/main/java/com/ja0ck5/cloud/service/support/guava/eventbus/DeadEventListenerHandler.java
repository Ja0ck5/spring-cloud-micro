package com.ja0ck5.cloud.service.support.guava.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
	* 监听无效时间处理
	*
	* @author sysu
	* @version 2017/7/13 11:46
	*/
public class DeadEventListenerHandler {

	public static final Logger log = LoggerFactory.getLogger(DeadEventListenerHandler.class);

	@Subscribe
	public void listen(DeadEvent deadEvent) {
		log.error("出现无效事件，{},{}", deadEvent.getEvent(), deadEvent.getSource());

	}

}
