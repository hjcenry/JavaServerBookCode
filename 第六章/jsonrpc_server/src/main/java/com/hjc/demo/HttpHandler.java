package com.hjc.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @ClassName: HttpServerHandler
 * @Description: netty处理器
 * @author 何金成
 * @date 2015年12月18日 下午6:27:06
 * 
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	public HttpHandlerImp handler = new HttpHandlerImp();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		handler.channelRead(ctx, msg);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		super.write(ctx, msg, promise);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		handler.exceptionCaught(ctx, cause);
	}

	public static void writeJSON(ChannelHandlerContext ctx,
			HttpResponseStatus status, Object msg) {
	}

	public static void writeJSON(ChannelHandlerContext ctx, Object msg) {
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			FullHttpRequest msg) throws Exception {
		handler.messageReceived(ctx, msg);
	}
}
