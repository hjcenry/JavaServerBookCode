package nettytest;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjc.herol.net.ProtoIds;

public class NettyClient {
	NioEventLoopGroup workGroup = new NioEventLoopGroup(0,
			Executors.newCachedThreadPool());// work线程组

	public void connect(final int port, final String host) throws Exception {
		final Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(workGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				// 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
				ch.pipeline().addLast(new HttpResponseDecoder());
				// 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
				ch.pipeline().addLast(new HttpRequestEncoder());
				ch.pipeline().addLast(new HttpInHandler());
			}
		});
		JSONObject obj = new JSONObject();
		obj.put("userid", 2001);
		// JSONObject dataJson = new JSONObject();
		// dataJson.put("game", "1.0.1");
		// dataJson.put("goods", ItemCode.YUAN_BAO+"#0#100");
		// obj.put("data", dataJson);
		final String data = JSON.toJSONString(obj);
		// 开启线程测试
		for (int i = 0; i < 1; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// ChannelFuture f = bootstrap
						// .connect(new InetSocketAddress(host, port));
						// Start the client.
						ChannelFuture f = bootstrap.connect(host, port).sync();
						URI uri = new URI("http://" + host + ":" + port + "");
						String msg = "data=" + data;
						DefaultFullHttpRequest request = new DefaultFullHttpRequest(
								HttpVersion.HTTP_1_1, HttpMethod.GET,
								uri.toASCIIString(), Unpooled.wrappedBuffer(msg
										.getBytes("UTF-8")));
						// 构建http请求
						request.setMethod(HttpMethod.POST);
						request.headers().set(HttpHeaders.Names.HOST, host);
						// request.headers().set(HttpHeaders.Names.CONNECTION,
						// HttpHeaders.Values.KEEP_ALIVE);
						request.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
								request.content().readableBytes());
						// 发送http请求
						f.channel().write(request);
						f.channel().flush();
						f.channel().closeFuture().sync();
					} catch (Exception e) {
						e.printStackTrace();
					}
					// 休眠30s
					// try {
					// Thread.sleep(30000);
					// } catch (InterruptedException e) {
					// e.printStackTrace();
					// }
				}
			});
			t.start();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 8586;
		new NettyClient().connect(port, "123.59.110.201");
	}
}
