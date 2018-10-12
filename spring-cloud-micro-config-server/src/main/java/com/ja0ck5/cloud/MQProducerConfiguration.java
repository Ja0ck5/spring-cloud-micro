package com.ja0ck5.cloud;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ja0ck5.cloud.property.ProducerProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Ja0ck5
 * @Description:
 * @Date: Created in 14:48 2018/9/26
 * @Modified By:
 */
@Slf4j
@Getter
@Setter
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ProducerProperties.class)
public class MQProducerConfiguration {

	private final ProducerProperties producerProperties;

	@Bean
	public DefaultMQProducer getRocketMQProducer() {
		String namesrvAddr = this.producerProperties.getNamesrvAddr();
		if (StringUtils.isEmpty(namesrvAddr)) {
		}
		String groupName = this.producerProperties.getGroupName();
		DefaultMQProducer producer;
		producer = new DefaultMQProducer(groupName);
		producer.setNamesrvAddr(namesrvAddr);
		// 如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
		// producer.setInstanceName(instanceName);
		if (this.producerProperties.getMaxMessageSize() != null) {
			producer.setMaxMessageSize(this.producerProperties.getMaxMessageSize());
		}
		if (this.producerProperties.getSendMsgTimeout() != null) {
			producer.setSendMsgTimeout(this.producerProperties.getSendMsgTimeout());
		}
		// 如果发送消息失败，设置重试次数，默认为2次
		if (this.producerProperties.getRetryTimesWhenSendFailed() != null) {
			producer.setRetryTimesWhenSendFailed(this.producerProperties.getRetryTimesWhenSendFailed());
		}

		try {
			producer.start();
			log.info(String.format("producer is start ! groupName:{},namesrvAddr:{}", groupName, namesrvAddr));
		} catch (MQClientException e) {
			log.error(String.format("producer is error {}", e));
		}
		return producer;
	}
}
