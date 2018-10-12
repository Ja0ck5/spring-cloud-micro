package com.ja0ck5.cloud.service.channel;

import com.ja0ck5.cloud.dao.UserInfoDao;
import com.ja0ck5.cloud.model.UserInfo;
import com.ja0ck5.cloud.model.cache.ICachedTail;
import com.ja0ck5.cloud.service.i.IinitChannelGroup;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.RedisProxy;

/**
 * Created by Jack on 2017/12/23.
 */
@Service
public class ChannelService implements ICachedTail, IinitChannelGroup {

	@Autowired
	protected UserInfoDao userInfoDao;
	@Autowired
	protected RedisProxy homeRedis;

	/**
	 * 获取 channel key
	 * 
	 * @param appId
	 * @param keySegment
	 * @param channelId
	 * @return
	 */
	public String getChannelKey(String appId, String keySegment, String channelId) {
		return new StringBuilder(appId).append(keySegment).append(channelId).toString();
	}

	/**
	 * TODO 设置 uid 与 channel 的对应关系
	 * 
	 * @param appId
	 * @param uid
	 * @param channel
	 * @return
	 */
	public Channel setChannelUid(long appId, long uid, Channel channel) {
		return null;
	}

	/**
	 * 根据 uid 获取 channel
	 * 
	 * @param uid
	 * @return
	 */
	public Channel getChannelByUid(long uid) {
		UserInfo userInfo = userInfoDao.getByUid(uid);
		if (null == userInfo) {
			return null;
		}
		String userChannelKey = key(String.valueOf(userInfo.getAppId()), userChannelTail);
		String channelIdCache = homeRedis.hget(userChannelKey, String.valueOf(uid));
		if (StringUtils.isBlank(channelIdCache)) {
			return null;
		}
		ChannelId channelIdInMap = CHANNEL_ID_MAP.get(channelIdCache);
		if (null == channelIdInMap) {
			return null;
		}
		return channelGroup.find(channelIdInMap);
	}
}
