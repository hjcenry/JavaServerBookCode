package com.hjc.herolpvp.net.http;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
public class HttpInHandler extends ChannelHandlerAdapter {

	public HttpInHandlerImp handler = new HttpInHandlerImp();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		handler.channelRead(ctx, msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		handler.exceptionCaught(ctx, cause);
	}

	public static void writeJSON(ChannelHandlerContext ctx,
			HttpResponseStatus status, Object msg) {
		HttpInHandlerImp.writeJSON(ctx, status, msg);
	}

	public static void writeJSON(ChannelHandlerContext ctx, Object msg) {
		HttpInHandlerImp.writeJSON(ctx, msg);
	}
}
