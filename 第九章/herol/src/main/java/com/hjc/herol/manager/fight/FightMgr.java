package com.hjc.herol.manager.fight;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;

import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.hjc.herol.manager.player.Player;
import com.hjc.herol.manager.treasure.TreasureMgr;
import com.hjc.herol.net.ProtoMessage;
import com.hjc.herol.net.http.HttpInHandler;
import com.hjc.herol.net.http.HttpServer;
import com.hjc.herol.util.mongo.MorphiaUtil;
import com.hjc.herol.util.redis.Redis;

public class FightMgr {
	private static FightMgr fightMgr;
	private static final Logger logger = LoggerFactory
			.getLogger(FightMgr.class);
	public Redis redis = Redis.getInstance();
	public Map<Integer, Integer> cupMap = new HashMap<Integer, Integer>();
	private Datastore ds = MorphiaUtil.ds;

	private FightMgr() {
	}

	public static FightMgr getInstance() {
		if (null == fightMgr) {
			fightMgr = new FightMgr();
		}
		return fightMgr;
	}

	public void initCsvData() {
		logger.info("FightMgr initCsvData");
	}

	public void initData() {
		logger.info("FightMgr initData");
		// 战斗结算浮动杯数
		Map<Integer, Integer> cupMap = new HashMap<Integer, Integer>();
		cupMap.put(500, 60);
		cupMap.put(600, 50);
		cupMap.put(800, 40);
		cupMap.put(1000, 30);
		cupMap.put(1500, 20);
		cupMap.put(2000, 10);
		this.cupMap = cupMap;
	}

	/**
	 * @Title: getServer
	 * @Description: 获取PVP服务器
	 * @param ctx
	 * @return void
	 * @throws
	 */
	public void getServer(ChannelHandlerContext ctx) {
		JSONArray ret = new JSONArray();
		ret.add(HttpServer.pvpIp);
		ret.add(HttpServer.pvpPort);
		HttpInHandler.writeJSON(ctx, ret);
	}

	/**
	 * @Title: fightOver
	 * @Description: 战斗结算
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void fightOver(ChannelHandlerContext ctx, ProtoMessage data) {
		long userid = data.getUserid();
		int result = data.getData().getIntValue("result");
		Player player = ds.find(Player.class).field("_id").equal(userid).get();
		// 计算奖杯
		int sum = 0;
		int cup = 0;
		for (int i = 0; i < cupMap.keySet().size(); i++) {
			int cups = cupMap.get(cupMap.keySet().toArray()[i]);
			if (player.cups >= sum && player.cups < sum + cups) {
				cup = i;
				break;
			}
			sum += cups;
		}
		player.cups = result == 1 ? player.cups + cup : player.cups - (cup - 5);
		// 获取宝箱
		TreasureMgr.getInstance().pickTreasure(userid);
		ds.save(player);
	}

}
