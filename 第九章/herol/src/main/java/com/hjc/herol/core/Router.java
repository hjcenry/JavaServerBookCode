package com.hjc.herol.core;

import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hjc.herol.manager.fight.FightMgr;
import com.hjc.herol.manager.hero.HeroMgr;
import com.hjc.herol.manager.player.PlayerMgr;
import com.hjc.herol.manager.treasure.TreasureMgr;
import com.hjc.herol.net.ProtoIds;
import com.hjc.herol.net.ProtoMessage;
import com.hjc.herol.net.http.HttpInHandler;

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
	public PlayerMgr playerMgr;
	public FightMgr fightMgr;
	public HeroMgr heroMgr;
	public TreasureMgr treasureMgr;

	private Router() {

	}

	public void initMgr() {
		playerMgr = PlayerMgr.getInstance();
		fightMgr = FightMgr.getInstance();
		heroMgr = HeroMgr.getInstance();
		treasureMgr = TreasureMgr.getInstance();
	}

	public static Router getInstance() {
		if (null == router) {
			router = new Router();
		}
		return router;
	}

	public void initCsvData() {
		// 初始化Csv
		playerMgr.initCsvData();
		fightMgr.initCsvData();
		heroMgr.initCsvData();
		treasureMgr.initCsvData();
	}

	public void initData() {
		// 初始化数值
		playerMgr.initData();
		fightMgr.initData();
		heroMgr.initData();
		treasureMgr.initData();
	}

	/**
	 * @Title: route
	 * @Description: 消息路由分发
	 * @param val
	 * @param ctx
	 *            void
	 * @throws
	 */
	public void route(String val, ChannelHandlerContext ctx) {
		ProtoMessage data = null;
		try {
			data = JSON.parseObject(val, ProtoMessage.class);
		} catch (Exception e) {
			logger.error("格式错误，需json格式数据");
			HttpInHandler.writeJSON(ctx, ProtoMessage.getErrorResp("json格式错误"));
			return;
		}
		if (data.getTypeid() == null) {
			logger.error("没有协议号");
			HttpInHandler.writeJSON(ctx, ProtoMessage.getErrorResp("协议号"));
			return;
		}
		switch (data.getTypeid()) {
		case ProtoIds.TEST:
			HttpInHandler.writeJSON(ctx, ProtoMessage.getSuccessResp());
			break;
		case ProtoIds.GET_PVP_SERVER:
			fightMgr.getServer(ctx);
			break;
		/** 个人信息 **/
		case ProtoIds.CREATE_ROLE:
			playerMgr.createRole(ctx, data);
			break;
		case ProtoIds.PLAYER_QUERY:
			playerMgr.queryPlayer(ctx, data);
			break;
		case ProtoIds.PLAYER_SET_GROUP:
			playerMgr.setFightGroup(ctx, data);
			break;
		/** 英雄卡牌 **/
		case ProtoIds.HERO_QUERY:
			heroMgr.queryHero(ctx, data);
			break;
		case ProtoIds.HERO_SET_GROUP:
			heroMgr.setHeroGroup(ctx, data);
			break;
		case ProtoIds.HERO_UP_LEVEL:
			heroMgr.upHeroLevel(ctx, data);
			break;
		/** 宝箱系统 **/
		case ProtoIds.TREASURE_QUERY:
			treasureMgr.queryTreasure(ctx, data);
			break;
		case ProtoIds.TREASURE_HERO_PICK:
			treasureMgr.pickHeroCard(ctx, data);
			break;
		default:
			logger.error("未知协议号:{}", data.getTypeid());
			HttpInHandler.writeJSON(ctx,
					ProtoMessage.getErrorResp("未知协议号" + data.getTypeid()));
			break;
		}
	}
}
