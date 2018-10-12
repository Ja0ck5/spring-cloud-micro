package com.ja0ck5.cloud.model;

/**
 * Created by Jack on 2017/12/22.
 */
public enum UriBaseParam {

	appId("appId"), //
	uid("uid"), //
	token("token"), //
	channel("channel"), // 渠道,不是消息channel
	os("os"), // 1=ios;2=adr
	ver("ver"), // 版本号
	pushToken("pushToken"), // 推送token
	other("other"), // 推送token
	ip("ip"),// 推送token
	;

	private String value;

	/**
	 * @return the {@link #value}
	 */
	public String getValue() {
		return value;
	}

	UriBaseParam(String value) {
		this.value = value;
	}

	public static UriBaseParam get(String value) {
		switch (value) {
		case "appId":
			return appId;
		case "uid":
			return uid;
		case "token":
			return token;
		case "channel":
			return channel;
		case "os":
			return os;
		case "ver":
			return ver;
		case "pushToken":
			return pushToken;
		case "ip":
			return ip;
		default:
			return other;
		}

	}

}
