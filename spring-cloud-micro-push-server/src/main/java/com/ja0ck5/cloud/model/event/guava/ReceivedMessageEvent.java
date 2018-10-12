package com.ja0ck5.cloud.model.event.guava;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Jack on 2017/12/25.
 */
public class ReceivedMessageEvent {

	protected ChannelHandlerContext ctx;

	protected String msgId;

    /**
     * @see com.ja0ck5.cloud.service.message.ResponseMessageService#sendReceivedMessage(ReceivedMessageEvent)
     * @param ctx
     * @param msgId
     */
	public ReceivedMessageEvent(ChannelHandlerContext ctx, String msgId) {
		this.ctx = ctx;
		this.msgId = msgId;
	}

	public ReceivedMessageEvent() {
	}

	/**
	 * @return the {@link #ctx}
	 */
	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	/**
	 * @param ctx
	 *            the {@link #ctx} to set
	 */
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	/**
	 * @return the {@link #msgId}
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId
	 *            the {@link #msgId} to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
}
