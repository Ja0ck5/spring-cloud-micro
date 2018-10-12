package com.ja0ck5.cloud.model.cache;

public interface ICachedTail {

	/**
	 * {key} => {field}=uid,-> {value}=channelId
	 */
	String userChannelTail = ":uid:channelId";

	String channelUserTail = ":channelId:uid";

	default String key(String head, String tail) {
		return new StringBuilder(head).append(tail).toString();
	}

}