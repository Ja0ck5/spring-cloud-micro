package com.ja0ck5.cloud.handler;

import com.ja0ck5.cloud.model.message.ReceivedMessage;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ja0ck5.cloud.dao.UserInfoDao;
import com.ja0ck5.cloud.model.cache.ICachedTail;
import com.ja0ck5.cloud.model.event.ReceivedEvent;
import com.ja0ck5.cloud.service.i.IJSONRender;
import com.ja0ck5.cloud.service.i.IinitChannelGroup;
import com.ja0ck5.cloud.service.session.SessionService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import redis.clients.jedis.RedisProxy;

/**
 * Created by Jack on 2017/12/28.
 */
@Service
@ChannelHandler.Sharable
public class ReceivedEventHandler extends SimpleChannelInboundHandler<Object>
		implements IJSONRender, IinitChannelGroup, ICachedTail {

	@Autowired
	SessionService sessionService;

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof ReceivedEvent) {
			ReceivedEvent receivedEvent = (ReceivedEvent) evt;
			Channel channelInGroup = sessionService.getChannelByUid(receivedEvent.getUid());
			ReceivedMessage receivedMessage = new ReceivedMessage(receivedEvent.getMsgId());
			receivedMessage.setMsg(receivedEvent.getMsg());
			channelInGroup.writeAndFlush(new TextWebSocketFrame(renderJSON(receivedMessage)));
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		ctx.writeAndFlush("ReceivedEventHandler send msg!!!");
	}
	/**
	 * 处理自定义事件
	 */
	// @Override public void userEventTriggered(ChannelHandlerContext ctx,
	// Object evt) throws Exception {
	//
	// if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.
	// HANDSHAKE_COMPLETE) { // 如果该事件握手成功，则从该 ChannelPipeline 中移除
	// HttpRequestHandler,因为将不会接收到任何的 // Http 消息了
	// ctx.pipeline().remove(HttpRequestHandler.class); // 通知所有已经连接的 WebSocket
	// 客户端新的客户端已经连接上了 group.writeAndFlush(new TextWebSocketFrame("Client " +
	// ctx.channel() + " joined")); // 将新的 WebSocket Channel 添加到 ChannelGroup
	// 中，以便它可以接收到所有的消息 group.add(ctx.channel()); } else {
	// super.userEventTriggered(ctx, evt); }
	//
	// }

}
