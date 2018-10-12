package com.ja0ck5.cloud;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
@RestController
public class SpringCloudMicroConfigServerApplication {

	/** 使用RocketMq的生产者 */
	@Autowired
	private DefaultMQProducer defaultMQProducer;

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMicroConfigServerApplication.class, args);
	}

	@GetMapping("/mq/send")
	public void send() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
		String msg = "demo msg test";
		log.info("开始发送消息：" + msg);
		Message sendMsg = new Message("allstar-message", "test", msg.getBytes());
		// 默认3秒超时
		SendResult sendResult = defaultMQProducer.send(sendMsg);
		log.info("消息发送响应信息：" + sendResult.toString());
	}

}
