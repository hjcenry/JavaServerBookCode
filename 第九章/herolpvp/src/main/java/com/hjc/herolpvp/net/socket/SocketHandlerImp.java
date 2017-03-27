package com.hjc.herolpvp.net.socket;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.group.ChannelGroup;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hjc.herolpvp.core.GameServer;
import com.hjc.herolpvp.core.Router;
import com.hjc.herolpvp.manager.fight.FightMgr;
import com.hjc.herolpvp.net.ProtoIds;
import com.hjc.herolpvp.net.ProtoMessage;
import com.hjc.herolpvp.task.ExecutorPool;
import com.hjc.herolpvp.util.Constants;
import com.hjc.herolpvp.util.encrypt.XXTeaCoder;

public class SocketHandlerImp {
	private static Logger log = LoggerFactory.getLogger(SocketHandlerImp.class);
	public static volatile boolean ENCRIPT_DECRIPT = false;

	public void channelRead(final ChannelHandlerContext ctx, final Object msg)
			throws Exception {
		ExecutorPool.channelHandleThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				if (!GameServer.shutdown) {// 服务器开启的情况下
					dataHandle(ctx, msg);
				} else {// 服务器已关闭
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("errMsg", "server closed");
					SocketHandler.writeJSON(ctx, jsonObject);
				}
			}
		});
	}

	/**
	 * @Title: codeFilter
	 * @Description: 编解码过滤
	 * @param val
	 * @return String
	 * @throws
	 */
	private String codeFilter(String val) {
		try {
			val = val.contains("%") ? URLDecoder.decode(val, "UTF-8") : val;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String valTmp = val;
		val = ENCRIPT_DECRIPT ? XXTeaCoder.decryptBase64StringToString(val,
				XXTeaCoder.key) : val;
		if (Constants.MSG_LOG_DEBUG) {
			if (val == null) {
				val = valTmp;
			}
		}
		return val;
	}

	/**
	 * @Title: dataHandle
	 * @Description: 数据处理
	 * @param ctx
	 * @param msg
	 *            void
	 * @throws
	 */
	private void dataHandle(final ChannelHandlerContext ctx, final Object msg) {
		String body = (String) msg;
		body = codeFilter(body);
		ProtoMessage data = null;
		try {
			data = JSON.parseObject(body, ProtoMessage.class);
		} catch (Exception e) {
			log.error("格式错误，需json格式数据");
			SocketHandler.writeJSON(ctx,
					ProtoMessage.getErrorResp("json格式错误," + body));
			return;
		}
		if (Constants.MSG_LOG_DEBUG) {
			if (data.getTypeid() != ProtoIds.TEST) {
				log.info("read :" + body);
			}
		}
		Router.getInstance().route(data, ctx);
	}

	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		log.info("ip:{}断开连接", ctx.channel().remoteAddress());
		Long userid = ChannelMgr.getInstance().findByChannel(ctx.channel()).userid;
		if (userid != null) {
			FightMgr.getInstance().exitPkSceneMap(userid);
			FightMgr.getInstance().exitWaitingUsers(userid);
		}
	}

	public void channelActive(ChannelHandlerContext ctx) {
		log.info("ip:{}建立连接", ctx.channel().remoteAddress());
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.error("ip:" + ctx.channel().remoteAddress() + "抛出异常", cause);
		Long userid = ChannelMgr.getInstance().findByChannel(ctx.channel()).userid;
		if (userid != null) {
			FightMgr.getInstance().exitPkSceneMap(userid);
			FightMgr.getInstance().exitWaitingUsers(userid);
		}
	}

	/**
	 * @Title: writeJSON
	 * @Description: 发送JSON消息
	 * @param ctx
	 * @param msg
	 *            void
	 * @throws
	 */
	public static void writeJSON(ChannelHandlerContext ctx, Object msg) {
		if (msg == null || msg instanceof String) {
			ctx.writeAndFlush(msg);
		} else {
			String sentMsg = JSON.toJSONString(msg);
			if (ctx.channel().isWritable()) {
				ctx.writeAndFlush(sentMsg);
				log.warn("channelId:{}", ctx.channel().id().asShortText());
			}
		}
	}

	/**
	 * @Title: writeJSON
	 * @Description: 群发JSON消息
	 * @param group
	 * @param msg
	 *            void
	 * @throws
	 */
	public static void writeJSON(ChannelGroup group, Object msg) {
		if (msg == null || msg instanceof String) {
			group.writeAndFlush(msg);
		} else {
			String sentMsg = JSON.toJSONString(msg);
			group.writeAndFlush(sentMsg);
		}
	}

	public void write(ChannelHandlerContext ctx, Object msg) {
		if (Constants.MSG_LOG_DEBUG) {
			String resp = (String) msg;
			log.info("ip:{},write:{}", ctx.channel().remoteAddress(), resp);
		}
	}
}
