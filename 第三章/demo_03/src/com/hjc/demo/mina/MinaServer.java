package com.hjc.demo.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {
	public void init() throws Exception {
		SocketAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime()
				.availableProcessors() + 1);
		// 设置解析器
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory()));
		// 绑定处理器Handler
		acceptor.setHandler(new MinaServerHandler());
		// 绑定8200端口
		acceptor.bind(new InetSocketAddress(8200));
		System.out.println("绑定端口8200");
	}

	public MinaServer() throws Exception {
		init();
	}

	public static void main(String[] args) throws Exception {
		new MinaServer();
		System.out.println("Mina服务器开启");
	}
}
