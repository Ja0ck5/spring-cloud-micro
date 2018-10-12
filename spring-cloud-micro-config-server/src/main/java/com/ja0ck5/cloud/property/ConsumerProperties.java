package com.ja0ck5.cloud.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Ja0ck5
 * @Description:
 * @Date: Created in 15:23 2018/9/26
 * @Modified By:
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "rocketmq.consumer")
public class ConsumerProperties {

	private String namesrvAddr;

	private String groupName;

	private int consumeThreadMin;

	private int consumeThreadMax;

	private String topics;

	private int consumeMessageBatchMaxSize;

}
