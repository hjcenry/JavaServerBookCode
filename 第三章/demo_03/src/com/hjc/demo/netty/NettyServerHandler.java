package com.hjc.demo.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class NettyServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("服务端收到消息：" + msg.toString());
		ctx.writeAndFlush("服务端收到了：" + msg.toString());
		ctx.writeAndFlush("msg from server");
		ctx.writeAndFlush("quit");
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		System.out.println("服务端发送消息：" + msg.toString());
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		System.out.println("客户端断开连接");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// System.out.println("channelActive");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// System.out.println("exceptionCaught");
	}
}