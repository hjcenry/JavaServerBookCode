package com.hjc.herol.net.http;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class HttpOutHandler extends ChannelHandlerAdapter {

	public HttpOutHandlerImp handler = new HttpOutHandlerImp();

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		super.write(ctx, msg, promise);
		handler.write(ctx, msg, promise);
	}

}
