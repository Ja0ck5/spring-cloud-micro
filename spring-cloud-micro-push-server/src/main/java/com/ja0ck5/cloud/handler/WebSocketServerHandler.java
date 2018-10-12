package com.ja0ck5.cloud.handler;

/**
 * Created by Jack on 2017/12/20.
 */

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpMethod.POST;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hazelcast.internal.networking.nio.NioChannelFactory;
import com.ja0ck5.cloud.model.event.ReceivedEvent;
import com.ja0ck5.cloud.model.message.Receiver;
import com.ja0ck5.cloud.model.message.Sender;
import com.ja0ck5.cloud.model.message.SentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.AsyncEventBus;
import com.ja0ck5.cloud.config.info.ServerInfo;
import com.ja0ck5.cloud.dao.UserInfoDao;
import com.ja0ck5.cloud.model.HeadUri;
import com.ja0ck5.cloud.model.UriBaseParam;
import com.ja0ck5.cloud.model.cache.ICachedTail;
import com.ja0ck5.cloud.model.user.UserInfoEvent;
import com.ja0ck5.cloud.service.channel.ChannelService;
import com.ja0ck5.cloud.service.i.IJSONRender;
import com.ja0ck5.cloud.service.i.IinitChannelGroup;
import com.ja0ck5.cloud.service.session.SessionService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import redis.clients.jedis.RedisProxy;

/**
 * Handles handshakes and messages
 */
@Service
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object>
		implements IinitChannelGroup, ICachedTail, IJSONRender {

	@Autowired
	protected RedisProxy homeRedis;
	@Autowired
	protected ServerInfo serverInfo;
	@Autowired
	protected UserInfoDao userInfoDao;
	@Autowired
	protected ChannelService channelService;
	@Autowired
	protected SessionService sessionService;

	@Autowired
	protected AsyncEventBus asyncEventBus;

	private WebSocketServerHandshaker handshaker;

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) {
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		} else if (msg instanceof WebSocketFrame) {
			handleWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		// Handle a bad request.
		if (!req.decoderResult().isSuccess()) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
			return;
		}

		// Allow only GET and POST methods.
		HttpMethod httpMethod = req.method();
		if (httpMethod != GET && httpMethod != POST) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
			return;
		}

		// 根路径页面
		String reqUri = req.uri();
		if (HeadUri.ROOT.equals(reqUri)) {
			// ByteBuf content =
			// WebSocketServerBenchmarkPage.getContent(getWebSocketLocation(req));
			ByteBuf content = Unpooled.copiedBuffer("<h1>test content</h1>", CharsetUtil.UTF_8);
			FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

			res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
			HttpUtil.setContentLength(res, content.readableBytes());

			sendHttpResponse(ctx, req, res);
			return;
		}

		if (reqUri.startsWith(HeadUri.CONNECT)) {
			// Handshake init
			handshakeInit(ctx, req);
		}
	}

	/**
	 * 连接握手 初始化会话信息
	 * 
	 * @param ctx
	 * @param req
	 */
	private void handshakeInit(ChannelHandlerContext ctx, FullHttpRequest req) {
		String reqUri = req.uri();
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req),
				null, true, 5 * 1024 * 1024);
		handshaker = wsFactory.newHandshaker(req);
		Channel channel = ctx.channel();
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel);
		} else {
			handshaker.handshake(channel, req).addListener(future -> {
				// 1.1 获取 uri 基础参数
				QueryStringDecoder queryDecoder = new QueryStringDecoder(reqUri);
				Map<String, List<String>> parameters = queryDecoder.parameters();
				// ip
				InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.remoteAddress();
				String ip = inetSocketAddress.getAddress().getHostAddress();
				parameters.put(UriBaseParam.ip.getValue(), Arrays.asList(ip));
				// appId
				String appId = parameters.get(UriBaseParam.appId.getValue()).get(0);
				// 初始化会话
				sessionService.initSession(channel, parameters, appId);
				// 初始化用户基础参数
				// asyncEventBus.post(new UserInfoEvent(parameters));
				ctx.pipeline().fireUserEventTriggered(new UserInfoEvent(parameters));
				// TODO 1.3 查看是否有未读消息，发送未读消息，并标记为已读(消息存储到数据库)
				// test 测试发消息
				// asyncEventBus.post(new ReceivedMessageEvent(ctx,
				// "11111111111"));

				ctx.pipeline().fireUserEventTriggered(
						new ReceivedEvent("假装收到了消息", Long.valueOf(parameters.get(UriBaseParam.uid.getValue()).get(0))));
				SentMessage sentMessage = new SentMessage();
				Sender sender = new Sender();
				sentMessage.setSender(sender);
				Receiver receiver = new Receiver();
				sentMessage.setReceiver(receiver);
				sentMessage.setContent("这个是内容啊");
				ctx.writeAndFlush(new TextWebSocketFrame(renderJSON(sentMessage)));

				//
				// try {
				// channel.write(new TextWebSocketFrame(
				// channel.id() + "这只是尝试一下能不能发出去+\n " +
				// URLDecoder.decode(reqUri, CharsetUtil.UTF_8.name())));
				//
				// // 根据 uid 获取 channel 并发送消息
				// Channel channelInGroup =
				// channelService.getChannelByUid(Long.valueOf(uidStr));
				// // 群发在线消息
				// channelInGroup.write(new TextWebSocketFrame(channel.id() +
				// "我是 channelGroup 群发出去的消息+\n "
				// + URLDecoder.decode(reqUri, CharsetUtil.UTF_8.name())));
				//
				// } catch (UnsupportedEncodingException e) {
				//
				// }
			});

		}
	}

	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

		Channel channel = ctx.channel();
		// Check for closing frame
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(channel, (CloseWebSocketFrame) frame.retain()).addListener(future -> {
				// 清除会话信息
				sessionService.cleanSessionByChannel(channel);
			});
			return;
		}
		if (frame instanceof PingWebSocketFrame) {
			ctx.write(new PongWebSocketFrame(frame.content().retain()));
			// 刷新用户Channel的关系(刷新会话)
			sessionService.refreshSession(channel);
			return;
		}
		if (frame instanceof TextWebSocketFrame) {
			// Echo the frame 交给下一个 TextWebSocketFrameHandler
			ctx.fireChannelRead(frame.retain());
			return;
		}
		if (frame instanceof BinaryWebSocketFrame) {
			// Echo the frame
			ctx.write(frame.retain());
		}
	}

	/**
	 * 发送 http 响应，如页面
	 * 
	 * @param ctx
	 * @param req
	 * @param res
	 */
	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
		// Generate an error page if response getStatus code is not OK (200).
		if (res.status().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			HttpUtil.setContentLength(res, res.content().readableBytes());
		}

		// Send the response and close the connection if necessary.
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		Channel channel = ctx.channel();
		// 清除会话信息
		sessionService.cleanSessionByChannel(channel);
		ctx.close();
	}

	private static String getWebSocketLocation(FullHttpRequest req) {
		String location = req.headers().get(HttpHeaderNames.HOST) + HeadUri.CONNECT;
		// TODO ssl
		// if (WebSocketServer.SSL) {
		// return "wss://" + location;
		// } else {
		// return "ws://" + location;
		// }
		return "ws://" + location;
	}
}
