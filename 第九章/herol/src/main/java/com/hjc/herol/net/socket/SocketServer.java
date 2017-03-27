package com.hjc.herol.net.socket;

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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class SocketServer {
	public static Logger log = LoggerFactory.getLogger(SocketServer.class);
	public static SocketServer inst;
	public static Properties p;
	public static int port;
	private NioEventLoopGroup bossGroup = new NioEventLoopGroup();
	private NioEventLoopGroup workGroup = new NioEventLoopGroup();

	private SocketServer() {
	}

	public static SocketServer getInstance() {
		if (inst == null) {
			inst = new SocketServer();
			inst.initData();
		}
		return inst;
	}

	public void initData() {
		try {
			p = readProperties();
			port = Integer.parseInt(p.getProperty("port"));
		} catch (IOException e) {
			log.error("socket配置文件读取错误");
			e.printStackTrace();
		}
	}

	// Test Code
	public static void main(String[] args) {
		SocketServer server = new SocketServer();
		SocketServer.port = 8700;
		server.start();
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
				pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
				pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
				// 业务逻辑处理
				pipeline.addLast(new SocketHandler());
			}
		});
		// 启动端口
		ChannelFuture future;
		try {
			future = bootstrap.bind(port).sync();
			if (future.isSuccess()) {
				log.info("端口{}已绑定", port);
			}
		} catch (InterruptedException e) {
			log.info("端口{}绑定失败", port);
		}
	}

	public void shut() {
		workGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
		// 关闭所有channel连接
		log.info("关闭所有channel连接");
		ChannelMgr.getInstance().closeAllChannel();
		log.info("端口{}已解绑", port);
	}

	/**
	 * 读配置socket文件
	 * 
	 * @return
	 * @throws IOException
	 */
	protected Properties readProperties() throws IOException {
		Properties p = new Properties();
		InputStream in = SocketServer.class
				.getResourceAsStream("/net.properties");
		Reader r = new InputStreamReader(in, Charset.forName("UTF-8"));
		p.load(r);
		in.close();
		return p;
	}
}
