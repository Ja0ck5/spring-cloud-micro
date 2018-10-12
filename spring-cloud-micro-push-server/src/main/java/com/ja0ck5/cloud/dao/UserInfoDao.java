package com.ja0ck5.cloud.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ja0ck5.cloud.model.UserInfo;

/**
 * Created by Jack on 2017/12/22.
 */
@Repository
public class UserInfoDao extends BaseHomeDao {

	public UserInfo getByUid(long uid) {
		String sql = "select * from userInfo where uid=" + uid;
		log.debug(sql);
		return getJdbcTemplate().queryForObject(sql, rowMapper);
	}

	public int updateByUid(UserInfo userInfo) {
		String sql = "replace into userInfo(uid,appId,serverId,ip,channel,os,ver,pushToken,token) values(?,?,?,?,?,?,?,?,?)";
		log.debug(sql);
		return getJdbcTemplate().update(sql, userInfo.getUid(),userInfo.getAppId(), userInfo.getServerId(), userInfo.getIp(),
				userInfo.getChannel(), userInfo.getOs(), userInfo.getVer(), userInfo.getPushToken(),
				userInfo.getToken());
	}

	protected RowMapper<UserInfo> rowMapper = (RowMapper<UserInfo>) (rs, i) -> {
		UserInfo model = new UserInfo();
		model.setUid(rs.getLong("uid"));
		model.setAppId(rs.getLong("appId"));
		model.setServerId(rs.getInt("serverId"));
		model.setIp(rs.getString("ip"));
		model.setChannel(rs.getString("channel"));
		model.setOs(rs.getInt("os"));
		model.setVer(rs.getInt("ver"));
		model.setPushToken(rs.getString("pushToken"));
		return model;
	};

}
