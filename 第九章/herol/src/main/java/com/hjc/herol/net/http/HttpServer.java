package com.hjc.herol.net.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServer {
	public static Logger log = LoggerFactory.getLogger(HttpServer.class);
	public static HttpServer inst;
	public static Properties p;
	public static String ip;
	public static int port;
	public static String pvpIp;
	public static int pvpPort;
	private NioEventLoopGroup bossGroup = null;
	private NioEventLoopGroup workGroup = null;

	private HttpServer() {

	}

	public static HttpServer getInstance() {
		if (inst == null) {
			inst = new HttpServer();
			inst.initData();
		}
		return inst;
	}

	public void initData() {
		try {
			p = readProperties();
			ip = p.getProperty("ip");
			port = Integer.parseInt(p.getProperty("port"));
			// pvp服务器ip端口
			pvpIp = p.getProperty("pvpIp");
			pvpPort = Integer.parseInt(p.getProperty("pvpPort"));
		} catch (IOException e) {
			log.error("socket配置文件读取错误");
			e.printStackTrace();
		}
	}

	public void start() {
		bossGroup = new NioEventLoopGroup(0, Executors.newCachedThreadPool());// boss线程组
		workGroup = new NioEventLoopGroup(0, Executors.newCachedThreadPool());// work线程组
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				/* http request解码 */
				pipeline.addLast("decoder", new HttpRequestDecoder());
				pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
				/* http response 编码 */
				pipeline.addLast("encoder", new HttpResponseEncoder());
				pipeline.addLast("http-chunked", new ChunkedWriteHandler());
				/* http response handler */
				pipeline.addLast("outbound", new HttpOutHandler());
				/* http request handler */
				pipeline.addLast("inbound", new HttpInHandler());
			}
		});
		log.info("端口{}已绑定", port);
		bootstrap.bind(port);
	}

	public void shut() {
		if (bossGroup != null && workGroup != null) {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
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
		InputStream in = HttpServer.class
				.getResourceAsStream("/net.properties");
		Reader r = new InputStreamReader(in, Charset.forName("UTF-8"));
		p.load(r);
		in.close();
		return p;
	}
}
