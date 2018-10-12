package com.ja0ck5.cloud.service.message;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.ja0ck5.cloud.model.event.guava.ReceivedMessageEvent;
import com.ja0ck5.cloud.model.message.ReceivedMessage;
import com.ja0ck5.cloud.service.i.IJSONRender;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Created by Jack on 2017/12/25.
 */
@Service
public class ResponseMessageService implements IJSONRender{

	@Autowired
	protected AsyncEventBus asyncEventBus;

	@PostConstruct
	public void init() {
		asyncEventBus.register(this);
	}

	/**
	 * 通知 sender 服务器已经收到消息
	 * 
	 * @param receivedMessageEvent
	 */
	@Subscribe
	@AllowConcurrentEvents
	private void sendReceivedMessage(final ReceivedMessageEvent receivedMessageEvent) {
		ChannelHandlerContext ctx = receivedMessageEvent.getCtx();
		ReceivedMessage receivedMessage = new ReceivedMessage(receivedMessageEvent.getMsgId());
		// ctx.write(new TextWebSocketFrame(renderData(receivedMessage)));
		Channel channel = ctx.channel();
		if (channel.isWritable()) {// prevent oom
			channel.writeAndFlush(new TextWebSocketFrame(renderData(receivedMessage)));
		}
	}

}
