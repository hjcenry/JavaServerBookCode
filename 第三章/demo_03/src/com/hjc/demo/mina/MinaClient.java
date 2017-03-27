package com.hjc.demo.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {

	public static void run(int index) {
		// 创建TCP/IP连接
		NioSocketConnector connector = new NioSocketConnector();
		// 创建接受数据的过滤器
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		// 设定这个过滤器将一行一行(/r/n)的读取数据
		chain.addLast("myChin", new ProtocolCodecFilter(
				new TextLineCodecFactory()));
		// 服务器的消息处理器：一个SamplMinaServerHander对象
		connector.setHandler(new MinaClientHandler(index));
		// set connect timeout
		connector.setConnectTimeout(30);
		// 连接到服务器：
		ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",
				8200));
		cf.awaitUninterruptibly();
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		connector.dispose();

	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 5; i++) {// 运行10个客户端
			run(i + 1);
		}
	}
}
