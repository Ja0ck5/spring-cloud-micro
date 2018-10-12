package com.ja0ck5.cloud.model.session;

import com.ja0ck5.cloud.model.UserInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * Created by Jack on 2018/1/16.
 */
public abstract class Session {

	protected UserInfo user;

	protected Channel channel;

	public Session(UserInfo user, Channel channel) {
		this.user = user;
		this.channel = channel;
	}

	public boolean isRegistered() {
		return this.channel.isRegistered();
	}

	public boolean isOpen() {
		return this.channel.isOpen();
	}

	public boolean isActive() {
		return this.channel.isActive();
	}

	public boolean isWritable() {
		return this.channel.isWritable();
	}

	public ChannelFuture write(Object msg) {
		return this.channel.write(msg);
	}

	public ChannelFuture writeAndFlush(Object msg) {
		return this.channel.writeAndFlush(msg);
	}

	public void safelyWrite(Object msg) {
		if (this.channel.isWritable()) {
			safelyWrite(msg);
		} else {
			// 保存 msg
			saveMsg(msg);
		}
	}

	/**
	 * 
	 * 保存消息
	 */
	public abstract void saveMsg(Object msg);

	/**
	 * 
	 * 获取未读消息
	 */
	public abstract Object getNotReadMsg();

	public Session() {
	}

	/**
	 * @return the {@link #user}
	 */
	public UserInfo getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the {@link #user} to set
	 */
	public void setUser(UserInfo user) {
		this.user = user;
	}

	/**
	 * @return the {@link #channel}
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the {@link #channel} to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
