package com.hjc.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyServer {
	public static int port;
	private static NioEventLoopGroup bossGroup = new NioEventLoopGroup();
	private static NioEventLoopGroup workGroup = new NioEventLoopGroup();

	public void initData() {
		port = 8300;
	}

	// Test Code
	public static void main(String[] args) {
		NettyServer server = new NettyServer();
		NettyServer.port = 8300;
		server.start();
		System.out.println("Netty服务端已启动");
	}

	public void start() {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 128);
		// 通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.SO_REUSEADDR, true);
		// 保持长连接状态
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				// 添加String编解码器
				pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
				pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
				// 业务逻辑处理
				pipeline.addLast(new NettyServerHandler());
			}
		});
		// 启动端口
		ChannelFuture future;
		try {
			future = bootstrap.bind(port).sync();
			if (future.isSuccess()) {
				System.out.println("端口" + port + "已绑定");
			}
		} catch (InterruptedException e) {
			System.out.println("端口" + port + "已绑定");
		}
	}

	public static void shut() {
		workGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
		System.out.println("端口" + port + "已解绑");
	}
}
