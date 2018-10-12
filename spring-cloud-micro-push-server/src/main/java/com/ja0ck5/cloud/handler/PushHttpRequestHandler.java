package com.ja0ck5.cloud.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Service;

/**
 * Created by Jack on 2017/11/30.
 */
@Service
public class PushHttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private WebSocketServerHandshaker webSocketServerHandshaker;

	private ChannelGroup group;

	public PushHttpRequestHandler(ChannelGroup group) {
		this.group = group;
	}

	private PushHttpRequestHandler() {
	}

	/**
	 * TODO
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// super.channelActive(ctx);
		System.out.println(ctx.channel().id());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		if (request instanceof FullHttpRequest) { // 传统的HTTP接入
			handleHttpRequest(ctx, request);
		} else if (request instanceof WebSocketFrame) { // WebSocket接入
			ctx.fireChannelRead(request.retain());
			// handleWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}

	/**
	 * TODO http
	 * 
	 * @param ctx
	 * @param request
	 */
	public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {

		String uri = request.uri();
		if (uri.startsWith("/connect")) {
			// 正常WebSocket的Http连接请求，构造握手响应返回
			WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
					"ws://" + request.headers().get(HttpHeaderNames.HOST), null, false);
			webSocketServerHandshaker = wsFactory.newHandshaker(request);
			webSocketServerHandshaker.handshake(ctx.channel(), request);
//			 如果该事件握手成功，则从该 ChannelPipeline 中移除 HttpRequestHandler,因为将不会接收到任何的
//			 Http 消息了
//			ctx.pipeline().remove(HttpRequestHandler.class);
			// 通知所有已经连接的 WebSocket 客户端新的客户端已经连接上了
			group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
			// 将新的 WebSocket Channel 添加到 ChannelGroup 中，以便它可以接收到所有的消息
			group.add(ctx.channel());
			// 判断 uri 如果请求了 WebSocket 协议升级，则增加引用计数(调用 retain 方法) 并将它传递给下一个
			ctx.fireChannelRead(request.retain());
			return;
		}

		// 处理 100 Continue 请求以符合 HTTP 1.1 规范
		if (HttpUtil.is100ContinueExpected(request)) {
			send100Continue(ctx);
		}

		DefaultFullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(),
				HttpResponseStatus.OK);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; chartset=UTF-8");
		ByteBuf byteBuf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
		response.content().writeBytes(byteBuf);
		byteBuf.release();
		boolean keepAlive = HttpUtil.isKeepAlive(request);
		if (keepAlive) {
			// 如果请求了 keep-alive 则添加所需要的 Http 头信息
			response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		}
		// 写 LastHttpContent 并冲刷到客户端
		ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		if (!keepAlive) {// 如果没有请求 keep-alive 则在写操作完成之后 关闭 Channel
			future.addListener(ChannelFutureListener.CLOSE);
		}

	}

	private void send100Continue(ChannelHandlerContext ctx) {
		DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.writeAndFlush(response);
	}

}
