package com.ja0ck5.cloud.model.event;

/**
 * Created by Jack on 2017/12/28.
 */
public class ReceivedEvent {

	protected String msg;

	protected long uid;

	protected String msgId;

	public ReceivedEvent() {
	}

	public ReceivedEvent(String msg) {
		this.msg = msg;
	}

	public ReceivedEvent(String msg, long uid) {
		this(msg);
		this.uid = uid;
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

	/**
	 * @return the {@link #uid}
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the {@link #uid} to set
	 */
	public void setUid(long uid) {
		this.uid = uid;
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
