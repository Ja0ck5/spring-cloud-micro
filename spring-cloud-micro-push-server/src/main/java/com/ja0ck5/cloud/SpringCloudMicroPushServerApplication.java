package com.ja0ck5.cloud;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ja0ck5.cloud.server.ChatServer;
import io.netty.channel.ChannelFuture;

@SpringBootApplication
public class SpringCloudMicroPushServerApplication implements CommandLineRunner {

	@Autowired
	ChatServer endpoint;

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMicroPushServerApplication.class, args);
	}

	// TODO 改成配置文件
	@Value("${push.server.port:8097}")
	int port;

	@Override
	public void run(String... strings) throws Exception {
		// start server
		startChatServer();
	}

	public void startChatServer() {
//		final ChatServer endpoint = new ChatServer();
		ChannelFuture future = endpoint.start(new InetSocketAddress(port));
		Runtime.getRuntime().addShutdownHook(new Thread(() -> endpoint.destroy()));
		System.out.println("localhost:" + port);
		future.channel().closeFuture().syncUninterruptibly();
	}
}
