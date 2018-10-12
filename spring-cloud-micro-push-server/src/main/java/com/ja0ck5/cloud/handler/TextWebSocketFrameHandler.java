package com.ja0ck5.cloud.handler;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ja0ck5.cloud.model.event.ReceivedEvent;
import com.ja0ck5.cloud.model.util.JSONUtil;
import com.ja0ck5.cloud.service.i.IJSONRender;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 处理 WebSocket 文本帧 Created by Jack on 2017/9/22.
 */
@Service
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> implements IJSONRender {

	public TextWebSocketFrameHandler() {
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	/**
	 * 处理自定义事件
	 */
	/*
	 * @Override public void userEventTriggered(ChannelHandlerContext ctx,
	 * Object evt) throws Exception {
	 * 
	 * if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.
	 * HANDSHAKE_COMPLETE) { // 如果该事件握手成功，则从该 ChannelPipeline 中移除
	 * HttpRequestHandler,因为将不会接收到任何的 // Http 消息了
	 * ctx.pipeline().remove(HttpRequestHandler.class); // 通知所有已经连接的 WebSocket
	 * 客户端新的客户端已经连接上了 group.writeAndFlush(new TextWebSocketFrame("Client " +
	 * ctx.channel() + " joined")); // 将新的 WebSocket Channel 添加到 ChannelGroup
	 * 中，以便它可以接收到所有的消息 group.add(ctx.channel()); } else {
	 * super.userEventTriggered(ctx, evt); }
	 * 
	 * }
	 */

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {

		// 原始消息
		String originalMessage = frame.text().trim();

		JSONObject jo = JSONUtil.isJson(originalMessage);
		// TODO 改成发消息到 个别客户端
		if (null != jo) {// 认为是 im 消息
			// 1.1 TODO jsonObject ==> sentMessage 这里告诉 sender 服务器已经收到消息
			ctx.pipeline().fireUserEventTriggered(new ReceivedEvent("我收到消息了,告诉你我是根据 receiver 的 uid 收到的",
					jo.getJSONObject("receiver").getLong("uid")));
			// 1.2 根据 jo 的 receivedUid 获取 channel

		} else {// common message

		}

		// 增加消息的引用计数，并将它写到 ChannelGroup 中所有已经连接的客户端
		// ctx.channel().writeAndFlush(msg.retain());
		// String request = ((TextWebSocketFrame) msg).text();
		// group.writeAndFlush(msg.retain());
		// 1. 需要根据 uid 获取到 channel
		// 2. 根据 channel 发消息给(uid)用户
		// ctx.channel().write(new TextWebSocketFrame("测试测试测试~"));

	}
}
