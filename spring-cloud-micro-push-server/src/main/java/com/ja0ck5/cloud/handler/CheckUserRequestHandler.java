package com.ja0ck5.cloud.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by Jack on 2017/11/30.
 */
@Service
@ChannelHandler.Sharable
public class CheckUserRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

	    // TODO 调用用户校验的接口
        int i = new Random().nextInt(10);
        if(i > 0){
            System.out.println("user pass!!!");
            ctx.fireChannelRead(request.retain());
        }
	}
}
