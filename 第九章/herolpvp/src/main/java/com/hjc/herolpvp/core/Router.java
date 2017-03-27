package com.hjc.herolpvp.core;

import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjc.herolpvp.manager.fight.FightMgr;
import com.hjc.herolpvp.net.ProtoIds;
import com.hjc.herolpvp.net.ProtoMessage;
import com.hjc.herolpvp.net.socket.SocketHandler;

/**
 * @ClassName: Router
 * @Description: 消息路由分发
 * @author 何金成
 * @date 2015年12月14日 下午7:12:57
 * 
 */
public class Router {
	private static Router router = new Router();
	public Logger logger = LoggerFactory.getLogger(Router.class);
	public FightMgr fightMgr;

	private Router() {

	}

	public void initMgr() {
		fightMgr = FightMgr.getInstance();
	}

	public static Router getInstance() {
		if (null == router) {
			router = new Router();
		}
		return router;
	}

	public void initCsvData() {// 初始化Csv
	}

	public void initData() {// 初始化数值
	}

	/**
	 * @Title: route
	 * @Description: 消息路由分发
	 * @param val
	 * @param ctx
	 *            void
	 * @throws
	 */
	public void route(ProtoMessage data, ChannelHandlerContext ctx) {
		switch (data.getTypeid()) {
		case ProtoIds.TEST:
			test(ctx);
			break;
		case ProtoIds.EXIT_SCENE:
			fightMgr.exitScene(data.getUserid());
			break;
		case ProtoIds.FIGHT_ENTER_SCENE:
			fightMgr.enterScene(ctx, data);
			break;
		case ProtoIds.FIGHT_SKILL:
			fightMgr.skill(ctx, data);
			break;
		case ProtoIds.FIGHT_PICK_HERO:
			fightMgr.pickHero(ctx, data);
			break;
		default:
			logger.error("未知协议号:{}", data.getTypeid());
			SocketHandler.writeJSON(ctx,
					ProtoMessage.getErrorResp("未知协议号" + data.getTypeid()));
			break;
		}
	}

	public void test(ChannelHandlerContext ctx) {
		SocketHandler.writeJSON(ctx, "delay");
	}
}
