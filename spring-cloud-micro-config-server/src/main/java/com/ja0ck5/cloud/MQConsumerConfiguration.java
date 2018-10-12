package com.ja0ck5.cloud;

import com.ja0ck5.cloud.property.ConsumerProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author: Ja0ck5
 * @Description:
 * @Date: Created in 14:54 2018/9/26
 * @Modified By:
 */
@Slf4j
@Getter
@Setter
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ConsumerProperties.class)
public class MQConsumerConfiguration {

	private final ConsumerProperties consumerProperties;

	@Autowired
	private MQConsumeMsgListenerProcessor mqMessageListenerProcessor;

	@Bean
	public DefaultMQPushConsumer getRocketMQConsumer() {
		String groupName = consumerProperties.getGroupName();
		if (StringUtils.isEmpty(groupName)) {
		}
		String namesrvAddr = consumerProperties.getNamesrvAddr();
		if (StringUtils.isEmpty(namesrvAddr)) {
		}
		String topics = consumerProperties.getTopics();
		if (StringUtils.isEmpty(topics)) {
		}
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
		consumer.setNamesrvAddr(namesrvAddr);
		consumer.setConsumeThreadMin(consumerProperties.getConsumeThreadMin());
		consumer.setConsumeThreadMax(consumerProperties.getConsumeThreadMax());
		consumer.registerMessageListener(mqMessageListenerProcessor);
		/**
		 * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费 如果非第一次启动，那么按照上次消费的位置继续消费
		 */
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		/**
		 * 设置消费模型，集群还是广播，默认为集群
		 */
		// consumer.setMessageModel(MessageModel.CLUSTERING);
		/**
		 * 设置一次消费消息的条数，默认为1条
		 */
		consumer.setConsumeMessageBatchMaxSize(consumerProperties.getConsumeThreadMax());
		try {
			/**
			 * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
			 */
			String[] topicTagsArr = topics.split(";");
			for (String topicTags : topicTagsArr) {
				String[] topicTag = topicTags.split("~");
				consumer.subscribe(topicTag[0], topicTag[1]);
			}
			consumer.start();
			log.info("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}", groupName, topics, namesrvAddr);
		} catch (MQClientException e) {
			log.error("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}", groupName, topics, namesrvAddr, e);
		}
		return consumer;
	}
}
