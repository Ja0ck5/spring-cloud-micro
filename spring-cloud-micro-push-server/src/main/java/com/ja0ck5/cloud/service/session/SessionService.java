package com.ja0ck5.cloud.service.session;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ja0ck5.cloud.dao.UserInfoDao;
import com.ja0ck5.cloud.model.UriBaseParam;
import com.ja0ck5.cloud.model.UserInfo;
import com.ja0ck5.cloud.model.cache.ICachedDefine;
import com.ja0ck5.cloud.model.cache.ICachedTail;
import com.ja0ck5.cloud.model.session.Session;
import com.ja0ck5.cloud.service.i.IJSONRender;
import com.ja0ck5.cloud.service.i.IinitChannelGroup;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import redis.clients.jedis.RedisPipeline;
import redis.clients.jedis.RedisProxy;

/**
 * Created by Jack on 2018/1/10.
 */
@Service
public class SessionService implements IJSONRender, IinitChannelGroup, ICachedTail {

	@Autowired
	UserInfoDao userInfoDao;
	@Autowired
	RedisProxy homeRedis;

	/**
	 * key={userChannelKey} field={uid} value={channelId} 获取用户id ChannelId
	 * 对应的key
	 * 
	 * @param appId
	 * @return
	 */
	public String getUserChannelKey(String appId) {
		return key(appId, userChannelTail);
	}

	/**
	 * key={channelUserKey} field={channelId} value={uid} 获取 ChannelId
	 * 用户id对应的key
	 * 
	 * @param appId
	 * @return
	 */
	public String getChannelUserKey(String appId) {
		return key(appId, channelUserTail);
	}

	public String getAppIdByChannelId(String channelId) {
		return homeRedis.hget(ICachedDefine.dw_push_channelId_appId, channelId);
	}

	/**
	 * 
	 * 根据 uid 获取 Channel
	 */
	public Channel getChannelByUid(long uid) {
		UserInfo userInfo = userInfoDao.getByUid(uid);
		String userChannelKey = key(String.valueOf(userInfo.getAppId()), userChannelTail);
		String channelIdCache = homeRedis.hget(userChannelKey, String.valueOf(userInfo.getUid()));
		ChannelId channelIdInMap = CHANNEL_ID_MAP.get(channelIdCache);
		return channelGroup.find(channelIdInMap);
	}

	/**
	 * 根据 uid 获取会话信息
	 * 
	 * @param uid
	 * @return
	 */
	/*public Session getSessionByUid(long uid) {
		UserInfo userInfo = userInfoDao.getByUid(uid);
		String userChannelKey = key(String.valueOf(userInfo.getAppId()), userChannelTail);
		String channelIdCache = homeRedis.hget(userChannelKey, String.valueOf(userInfo.getUid()));
		ChannelId channelIdInMap = CHANNEL_ID_MAP.get(channelIdCache);
		Channel channel = channelGroup.find(channelIdInMap);
		return new Session(userInfo, channel);
	}*/

	/**
	 * 根据 channelId 获取 uid
	 */
	public long getUidByChannelId(String channelId) {
		String appId = getAppIdByChannelId(channelId);
		if (StringUtils.isBlank(appId)) {
			return 0;
		}
		String channelUserKey = getChannelUserKey(appId);
		String uidStr = homeRedis.hget(channelUserKey, channelId);
		if (StringUtils.isBlank(uidStr)) {
			return 0;
		}
		return NumberUtils.toLong(uidStr);
	}

	/**
	 * 根据 channelId 获取 userInfo
	 * 
	 * @param channelId
	 * @return
	 */
	public UserInfo getUserInfoByChannelId(String channelId) {
		long uid = getUidByChannelId(channelId);
		return userInfoDao.getByUid(uid);
	}

	/*public Optional<Session> getSessionByChannelId(String channelId) {
		Session session;
		String appId = getAppIdByChannelId(channelId);
		if (StringUtils.isBlank(appId)) {
			return null;
		}
		// user
		UserInfo userInfo = getUserInfoByChannelId(channelId);
		if (null == userInfo) {
			return Optional.ofNullable(null);
		}
		// channel
		ChannelId channelIdInMap = CHANNEL_ID_MAP.get(channelId);
		Channel channel = channelGroup.find(channelIdInMap);
		if (null == channel) {
			return Optional.ofNullable(null);
		}
		session = new Session(userInfo, channel);
		return Optional.ofNullable(session);
	}*/

	// TODO 加入 channel 与 user 关系缓存
	// TODO 删除 channel 与 user 关系缓存

	/**
	 * 刷新 用户 和 channel 的关系
	 * 
	 * @param channel
	 */
	public void refreshSession(Channel channel) {
		ChannelId channelId = channel.id();
		String channelIdLongText = channelId.asLongText();
		String appId = getAppIdByChannelId(channelIdLongText);
		if (StringUtils.isBlank(appId)) {
			return;
		}
		CHANNEL_ID_MAP.putIfAbsent(channelIdLongText, channel.id());
		channelGroup.add(channel);// 不能只添加 需要移除无效链接
		// 根据 channel 找到 user 然后 刷新 user 和 channel 对应的关系
		long uid = getUidByChannelId(channelIdLongText);
		String userChannelKey = key(appId, userChannelTail);
		homeRedis.hset(userChannelKey, String.valueOf(uid), channelIdLongText);
	}

	/**
	 * 初始化会话参数
	 * 
	 * @param channel
	 *            channel
	 * @param parameters
	 *            uri参数
	 * @param appId
	 *            appId
	 */
	public void initSession(Channel channel, Map<String, List<String>> parameters, String appId) {
		// 1.2 准备 key
		String userChannelKey = key(appId, userChannelTail);

		String channelIdKey = channel.id().asLongText();

		// userId -> channelIdKey
		String uidStr = parameters.get(UriBaseParam.uid.getValue()).get(0);

		// 1.3 去除之前存在的连接 TODO 根据 uid 判断用户上一次所在的机器，然后清除那台机器的无效channel
		String channelIdKeyOldCache = homeRedis.hget(userChannelKey, uidStr);
		if (StringUtils.isNotBlank(channelIdKeyOldCache)) {
			ChannelId channelId = CHANNEL_ID_MAP.get(channelIdKeyOldCache);
			if (null != channelId) {
				Channel oldChannelInGroup = channelGroup.find(channelId);
				if (null != oldChannelInGroup) {
					if (oldChannelInGroup.isActive()) {
						oldChannelInGroup.close();
					}
				}
				channelGroup.remove(oldChannelInGroup);
				CHANNEL_ID_MAP.putIfAbsent(channelIdKey, channel.id());
			}
		}

		// channelId = appId
		String channelAppIdKey = ICachedDefine.dw_push_channelId_appId;
		homeRedis.hset(channelAppIdKey, channelIdKey, appId);
		// 1.4 缓存 user channel 关系
		homeRedis.hset(userChannelKey, uidStr, channelIdKey);
		// channelIdKey -> userId
		String channelUserKey = key(appId, channelUserTail);
		homeRedis.hset(channelUserKey, channelIdKey, uidStr);
		// channelIdKey -> channel 为了以后可以取出来用
		channelGroup.add(channel);
		CHANNEL_ID_MAP.putIfAbsent(channelIdKey, channel.id());
	}

	/**
	 * 清除会话信息
	 * 
	 * @param channel
	 */
	public void cleanSessionByChannel(Channel channel) {
		String channelIdKey = channel.id().asLongText();
		// 1.1 清除 redis 消息
		String channelAppIdKey = ICachedDefine.dw_push_channelId_appId;
		// appId
		String appId = getAppIdByChannelId(channelAppIdKey);
		// 1.1.1 channelId = appId
		homeRedis.hdel(channelAppIdKey, channelIdKey);
		// 1.1.2 user channel 关系
		String userChannelKey = key(appId, userChannelTail);
		// uid getUidByChannelId
		long uid = getUidByChannelId(channelIdKey);
		homeRedis.hdel(userChannelKey, String.valueOf(uid));
		// 1.1.3 channelIdKey -> userId
		String channelUserKey = key(appId, channelUserTail);
		homeRedis.hdel(channelUserKey, channelIdKey);

		// 1.2 清除 channel
		CHANNEL_ID_MAP.remove(channelIdKey);
		channelGroup.remove(channel);
	}
}
