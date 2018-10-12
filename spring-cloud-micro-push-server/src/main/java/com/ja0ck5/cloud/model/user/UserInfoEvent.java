package com.ja0ck5.cloud.model.user;

import java.util.List;
import java.util.Map;

/**
 * 用户初始化事件
 * 
 * Created by Jack on 2017/12/23.
 */
public class UserInfoEvent {

	public UserInfoEvent() {
	}

	/**
	 * @see com.ja0ck5.cloud.handler.UserInfoEventHandler#initUserInfo(Map)
	 */
	public UserInfoEvent(Map<String, List<String>> parameters) {
		this.parameters = parameters;
	}

	/**
	 * 基础参数
	 */
	protected Map<String, List<String>> parameters;

	/**
	 * @return the {@link #parameters}
	 */
	public Map<String, List<String>> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters
	 *            the {@link #parameters} to set
	 */
	public void setParameters(Map<String, List<String>> parameters) {
		this.parameters = parameters;
	}

}
