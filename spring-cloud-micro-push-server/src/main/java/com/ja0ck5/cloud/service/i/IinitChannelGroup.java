package com.ja0ck5.cloud.service.i;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

/**
 * Created by Jack on 2017/12/22.
 */
public interface IinitChannelGroup {

	ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

	/**
	 * 暂时用来缓存 channel 因为 channel 无法序列化
	 */
	Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

	/**
	 * 暂时用来缓存 channelId 用于在 channelGroup 中查找 channel
	 */
	Map<String, ChannelId> CHANNEL_ID_MAP = new ConcurrentHashMap<>();

	ServerBootstrap bootstrap = new ServerBootstrap();

}
