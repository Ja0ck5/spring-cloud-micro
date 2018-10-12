package com.ja0ck5.cloud.model;

import java.util.Date;

/**
 * Created by Jack on 2017/12/22.
 */
public class UserInfo {

	/**
	 * 用户id
	 */
	protected long uid;

	/**
	 * appId
	 */
	protected long appId;

	/**
	 * 用户所在的服务器的id
	 */
	protected int serverId;

	/**
	 * 用户ip地址
	 */
	protected String ip;

	/**
	 * 渠道,不是消息channel
	 */
	protected String channel;

	/**
	 * 版本号
	 */
	protected int ver;

	/**
	 * 1:ios;2:adr
	 */
	protected int os;

	/**
	 * 更新时间
	 */
	protected Date updateTime;

	/**
	 * 推送token
	 */
	protected String pushToken;

	/**
	 * 登录态token
	 */
	protected String token;

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
	 * @return the {@link #serverId}
	 */
	public int getServerId() {
		return serverId;
	}

	/**
	 * @param serverId
	 *            the {@link #serverId} to set
	 */
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	/**
	 * @return the {@link #ip}
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the {@link #ip} to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the {@link #updateTime}
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the {@link #updateTime} to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the {@link #channel}
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the {@link #channel} to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the {@link #ver}
	 */
	public int getVer() {
		return ver;
	}

	/**
	 * @param ver
	 *            the {@link #ver} to set
	 */
	public void setVer(int ver) {
		this.ver = ver;
	}

	/**
	 * @return the {@link #os}
	 */
	public int getOs() {
		return os;
	}

	/**
	 * @param os
	 *            the {@link #os} to set
	 */
	public void setOs(int os) {
		this.os = os;
	}

	/**
	 * @return the {@link #pushToken}
	 */
	public String getPushToken() {
		return pushToken;
	}

	/**
	 * @param pushToken
	 *            the {@link #pushToken} to set
	 */
	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	/**
	 * @return the {@link #token}
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the {@link #token} to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the {@link #appId}
	 */
	public long getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the {@link #appId} to set
	 */
	public void setAppId(long appId) {
		this.appId = appId;
	}
}
