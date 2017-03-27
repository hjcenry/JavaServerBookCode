package nettytest;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjc.herolpvp.net.ProtoIds;

public class NettyClient {
	public static NioEventLoopGroup workGroup = new NioEventLoopGroup(0,
			Executors.newCachedThreadPool());// work线程组

	public void connect(final int port, final String host) throws Exception {
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

		// 发起异步连接操作
		b.connect(host, port);
		// 当代客户端链路关闭
		// f.channel().closeFuture().sync();
	}

	public static void main(String[] args) throws Exception {
		int port = 8700;
		new NettyClient().connect(port, "localhost");
	}
}
