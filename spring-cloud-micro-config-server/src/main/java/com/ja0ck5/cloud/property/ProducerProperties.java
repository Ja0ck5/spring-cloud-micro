package com.ja0ck5.cloud.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Ja0ck5
 * @Description:
 * @Date: Created in 15:17 2018/9/26
 * @Modified By:
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "rocketmq.producer")
public class ProducerProperties {

    /**
     * 发送同一类消息的设置为同一个group，保证唯一,默认不需要设置，rocketmq会使用ip@pid(pid代表jvm名字)作为唯一标示
     */
    private String groupName;

    private String namesrvAddr;
    /**
     * 消息最大大小，默认4M
     */
    private Integer maxMessageSize;
    /**
     * 消息发送超时时间，默认3秒
     */
    private Integer sendMsgTimeout;
    /**
     * 消息发送失败重试次数，默认2次
     */
    private Integer retryTimesWhenSendFailed;

}
