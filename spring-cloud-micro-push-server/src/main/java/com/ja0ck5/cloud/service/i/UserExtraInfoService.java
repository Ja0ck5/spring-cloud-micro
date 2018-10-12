package com.ja0ck5.cloud.service.i;

/**
 * Created by Jack on 2017/12/22.
 */
public interface UserExtraInfoService {

    /**
     * TODO 用户所属服务器
     */
    void belongServer();

    /**
     * TODO 用户坐标信息
     */
    void coordinate();

    /**
     * TODO 用户未读消息
     */
    void unReadMessage();

    /**
     * TODO 用户用于对应 channel 的 key
     */
    void key();
}
