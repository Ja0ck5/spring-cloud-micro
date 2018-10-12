package com.ja0ck5.cloud.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ja0ck5.cloud.config.info.ServerInfo;
import com.ja0ck5.cloud.dao.UserInfoDao;
import com.ja0ck5.cloud.model.UriBaseParam;
import com.ja0ck5.cloud.model.UserInfo;
import com.ja0ck5.cloud.model.cache.ICachedTail;
import com.ja0ck5.cloud.model.user.UserInfoEvent;
import com.ja0ck5.cloud.service.i.IJSONRender;
import com.ja0ck5.cloud.service.i.IinitChannelGroup;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Jack on 2017/12/28.
 */
@Service
@ChannelHandler.Sharable
public class UserInfoEventHandler extends SimpleChannelInboundHandler<Object>
		implements IJSONRender, IinitChannelGroup, ICachedTail {

	@Autowired
	protected ServerInfo serverInfo;
	@Autowired
	protected UserInfoDao userInfoDao;

	/**
	 * 处理自定义的 用户信息事件
	 * 
	 * @param ctx
	 * @param evt
	 * @throws Exception
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof UserInfoEvent) {
			initUserInfo(((UserInfoEvent) evt).getParameters());
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		ctx.writeAndFlush("ReceivedEventHandler send msg!!!");
	}

	public void initUserInfo(Map<String, List<String>> parameters) {
		// 1.2.2 记录用户连接到的机器 id ip 入库? 入缓存?
		UserInfo userInfo = new UserInfo();
		// uid
		userInfo.setUid(Long.valueOf(parameters.get(UriBaseParam.uid.getValue()).get(0)));
		// appId
		userInfo.setAppId(Long.valueOf(parameters.get(UriBaseParam.appId.getValue()).get(0)));
		// serverId
		userInfo.setServerId(serverInfo.getServerId());
		userInfo.setIp(parameters.get(UriBaseParam.ip.getValue()).get(0));
		// channel
		userInfo.setChannel(parameters.get(UriBaseParam.channel.getValue()).get(0));
		// os
		userInfo.setOs(Integer.parseInt(parameters.get(UriBaseParam.os.getValue()).get(0)));
		// ver
		userInfo.setVer(Integer.parseInt(parameters.get(UriBaseParam.ver.getValue()).get(0)));
		// pushToken
		userInfo.setPushToken(parameters.get(UriBaseParam.pushToken.getValue()).get(0));
		userInfo.setToken(parameters.get(UriBaseParam.token.getValue()).get(0));
		// 入库
		userInfoDao.updateByUid(userInfo);
	}

}
