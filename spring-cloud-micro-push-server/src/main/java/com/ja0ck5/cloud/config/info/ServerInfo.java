package com.ja0ck5.cloud.config.info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Jack on 2017/12/22.
 */
@Component
public class ServerInfo {

	@Value("${whale.server.id:1}")
	public int serverId;

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
}
