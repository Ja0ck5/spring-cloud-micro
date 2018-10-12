package com.ja0ck5.cloud.model;

import io.netty.channel.Channel;

/**
 * Created by Jack on 2017/12/22.
 */
public class WrapChannel {

	public Channel channel;

    public WrapChannel(Channel channel) {
        this.channel = channel;
    }

    public WrapChannel() {
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
