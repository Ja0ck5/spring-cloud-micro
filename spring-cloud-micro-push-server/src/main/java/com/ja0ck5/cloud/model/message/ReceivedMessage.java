package com.ja0ck5.cloud.model.message;

/**
 * Created by Jack on 2017/12/25.
 */
public class ReceivedMessage {

	/**
	 * 消息 id
	 */
	protected String msgId;

	protected String msg;

	/**
	 * 发送时间
	 */
	protected long sendTime;

	public ReceivedMessage(String msgId) {
		this.msgId = msgId;
		this.sendTime = System.currentTimeMillis();
	}

	public ReceivedMessage() {
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

	/**
	 * @return the {@link #sendTime}
	 */
	public long getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime
	 *            the {@link #sendTime} to set
	 */
	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return the {@link #msg}
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the {@link #msg} to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
