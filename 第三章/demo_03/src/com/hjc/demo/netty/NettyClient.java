package com.hjc.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClient {
	private ScheduledExecutorService executor = Executors
			.newScheduledThreadPool(1);
	public static final int PORT = 8300;
	public static final String IP = "localhost";

	public static EventLoopGroup group = new NioEventLoopGroup();

	public void connect(int port, String host, final int index)
			throws Exception {
		// 配置客户端NIO线程组
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							// 添加String编解码器
							pipeline.addLast(new StringDecoder(
									CharsetUtil.UTF_8));
							pipeline.addLast(new StringEncoder(
									CharsetUtil.UTF_8));
							// 业务逻辑处理
							pipeline.addLast(new NettyClientHandler(index));
						}
					});
			// 发起异步连接操作
			ChannelFuture future = b.connect(new InetSocketAddress(host, port));
			future.channel().closeFuture().sync();
		} finally {
			// 所有资源释放完成之后，清空资源，再次发起重连操作
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(1);
						try {
							// 以下代码可以再channel断开的时候自动重连
							// connect(PORT, IP, index);// 发起重连操作
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 3; i++) {
			new NettyClient().connect(PORT, IP, (i + 1));
		}
	}
}
