package com.ja0ck5.cloud.server;

import java.net.InetSocketAddress;

import com.hazelcast.internal.networking.nio.NioChannelFactory;
import com.ja0ck5.cloud.initializer.WanheServerInitializer;
import com.ja0ck5.cloud.service.i.IinitChannelGroup;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatServer  implements IinitChannelGroup  {

	@Autowired
	WanheServerInitializer wanheServerInitializer;

	// /**
	// * 创建 DefaultChannelGroup 其将保存所有已经连接的 WebSocket Channel
	// */
	// private final ChannelGroup channelGroup = new
	// DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

	private EventLoopGroup boss = new NioEventLoopGroup();
	private EventLoopGroup worker = new NioEventLoopGroup();

	private Channel channel;

	public ChannelFuture start(InetSocketAddress address) {
//		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, worker)
				.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, //
						new WriteBufferWaterMark(8 * 1024, 32 * 1024))// 设置水位
				// Channel 的分配使用池化的 ByteBuf
				.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				.channel(NioServerSocketChannel.class).childHandler(createInitializer());
		ChannelFuture future = bootstrap.bind(address);
		future.syncUninterruptibly();
		channel = future.channel();
		return future;
	}

	protected ChannelInitializer<Channel> createInitializer() {
		// return new WanheServerInitializer(/*channelGroup*/);
		return wanheServerInitializer;
	}

	/**
	 * 处理服务器关闭，并释放所有资源
	 */
	public void destroy() {
		if (null != channel) {
			channel.close();
		}
		// channelGroup.close();
		boss.shutdownGracefully();
		worker.shutdownGracefully();
	}

	/**
	 * 放置 application bootstrap
	 * 
	 * @param args
	 * @throws Exception
	 */
	/*
	 * public static void main(String[] args) throws Exception { if (args.length
	 * != 1) { System.err.println("Please give port as argument");
	 * System.exit(1); }
	 * 
	 * int port = Integer.parseInt(args[0]); final ChatServer endpoint = new
	 * ChatServer(); ChannelFuture future = endpoint.start(new
	 * InetSocketAddress(port)); Runtime.getRuntime().addShutdownHook(new
	 * Thread(() -> endpoint.destroy()));
	 * future.channel().closeFuture().syncUninterruptibly(); }
	 */

}