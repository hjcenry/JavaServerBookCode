package com.hjc.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {
	public static NioEventLoopGroup workGroup;
	private static ChannelFuture futrue;

	public void connect(final int port, final String host) throws Exception {
		workGroup = new NioEventLoopGroup();// work线程组
		// 配置客户端NIO线程组
		Bootstrap b = new Bootstrap();
		b.group(workGroup).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(new StringEncoder());
						ch.pipeline().addLast(new SocketInHandler());
					}
				});
		// 绑定端口、同步等待
		futrue = b.connect(host, port);
	}

	public static void shutdown() {
		// 等待服务监听端口关闭
		try {
			futrue.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 8700;
		new NettyClient().connect(port, "localhost");
	}
}
