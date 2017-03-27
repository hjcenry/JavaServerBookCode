package com.hjc.demo.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class NettyClientHandler extends ChannelHandlerAdapter {

	public int index = 0;

	public NettyClientHandler(int index) {
		this.index = index;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("客户端" + index + "收到消息：" + msg.toString());
		if (msg.toString().equals("quit")) {
			ctx.channel().close();
			// NettyClient.group.shutdownGracefully();
		} else {
			ctx.writeAndFlush("msg from client");
		}
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		System.out.println("客户端" + index + "发送消息：" + msg.toString());
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		System.out.println("客户端" + index + "断开连接");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端" + index + "连接上了");
		ctx.writeAndFlush("客户端发送的消息，服务端接收到了吗？");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println("exceptionCaught");
	}
}