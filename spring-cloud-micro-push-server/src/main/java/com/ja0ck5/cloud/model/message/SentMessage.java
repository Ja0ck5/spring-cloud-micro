package com.ja0ck5.cloud.model.message;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by Jack on 2017/12/25.
 */
public class SentMessage {

	public SentMessage(JSONObject jsonObject/* ,IUserData */) {
		long uid = jsonObject.getLongValue("uid");
		this.content = jsonObject.getString("content");
	}

	public SentMessage() {
	}

	protected long id;

	// sender
	protected Sender sender;
	// receiver
	protected Receiver receiver;

	/**
	 * 客户端的消息 id
	 */
	protected long msgId;
	/**
	 * 消息类型;1:im;2:通知(Notification);3:普通推送
	 */
	protected int msgType;

	/**
	 * 消息内容
	 */
	protected String content;

	/**
	 * 内容类型;0:图片;1:文本;2:音频;3:视频;4:图文;
	 */
	protected int contentType;

	protected Date createTime;

	/**
	 * @return the {@link #id}
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the {@link #id} to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the {@link #sender}
	 */
	public Sender getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the {@link #sender} to set
	 */
	public void setSender(Sender sender) {
		this.sender = sender;
	}

	/**
	 * @return the {@link #receiver}
	 */
	public Receiver getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver
	 *            the {@link #receiver} to set
	 */
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the {@link #msgType}
	 */
	public int getMsgType() {
		return msgType;
	}

	/**
	 * @param msgType
	 *            the {@link #msgType} to set
	 */
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	/**
	 * @return the {@link #contentType}
	 */
	public int getContentType() {
		return contentType;
	}

	/**
	 * @param contentType
	 *            the {@link #contentType} to set
	 */
	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the {@link #createTime}
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the {@link #createTime} to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the {@link #msgId}
	 */
	public long getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId
	 *            the {@link #msgId} to set
	 */
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the {@link #content}
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the {@link #content} to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
