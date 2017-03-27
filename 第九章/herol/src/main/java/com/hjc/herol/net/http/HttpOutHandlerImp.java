package com.hjc.herol.net.http;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjc.herol.util.Constants;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledUnsafeDirectByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;

public class HttpOutHandlerImp {
	public Logger logger = LoggerFactory.getLogger(HttpOutHandlerImp.class);

	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		if (Constants.MSG_LOG_DEBUG) {
			DefaultFullHttpResponse resp = (DefaultFullHttpResponse) msg;
			logger.info("ip:{},write:{}", ctx.channel().remoteAddress(), resp
					.content().toString(Charset.forName("UTF-8")));
		}
	}
}
