package com.ja0ck5.cloud.handler;

import java.util.Random;

import io.netty.channel.ChannelHandler;
import org.springframework.stereotype.Service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Created by Jack on 2017/11/30.
 */
@Service
@ChannelHandler.Sharable
public class CheckAppInfoHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

	    // TODO 调用 app校验的接口
        int i = new Random().nextInt(10);
        if(i > 0){
            System.out.println("app pass!!!");
            ctx.fireChannelRead(request.retain());
        }
	}
}
