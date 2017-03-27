package com.hjc.herol.manager.treasure;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.hjc.herol.manager.hero.HeroMgr;
import com.hjc.herol.manager.player.Player;
import com.hjc.herol.net.ProtoMessage;
import com.hjc.herol.net.http.HttpInHandler;
import com.hjc.herol.template.Treasure;
import com.hjc.herol.util.csv.TempletService;
import com.hjc.herol.util.mongo.MorphiaUtil;

public class TreasureMgr {
	private static TreasureMgr treasureMgr;
	private static final Logger logger = LoggerFactory
			.getLogger(TreasureMgr.class);
	private Datastore ds = MorphiaUtil.ds;
	public Map<Integer, Treasure> treasureMap = new HashMap<Integer, Treasure>();
	public Map<Integer, Integer> treasurePropertyMap = new HashMap<Integer, Integer>();
	public Map<Integer, Integer> propertyMap = new HashMap<Integer, Integer>();

	private TreasureMgr() {
	}

	public static TreasureMgr getInstance() {
		if (null == treasureMgr) {
			treasureMgr = new TreasureMgr();
		}
		return treasureMgr;
	}

	public void initData() {
		logger.info("TreasureMgr initData");
		// 三张卡的抽卡概率
		Map<Integer, Integer> treasurePropertyMap = new HashMap<Integer, Integer>();
		treasurePropertyMap.put(1, 100);
		treasurePropertyMap.put(2, 40);
		treasurePropertyMap.put(3, 20);
		this.treasurePropertyMap = treasurePropertyMap;
		// 获取宝箱概率
		Map<Integer, Integer> propertyMap = new HashMap<Integer, Integer>();
		propertyMap.put(1, 50);
		propertyMap.put(2, 20);
		propertyMap.put(3, 15);
		propertyMap.put(4, 10);
		propertyMap.put(5, 5);
		this.propertyMap = propertyMap;
	}

	public void initCsvData() {
		logger.info("TreasureMgr initCsvData");
		Map<Integer, Treasure> treasureMap = new HashMap<Integer, Treasure>();
		List<Treasure> treasures = TempletService.listAll(Treasure.class
				.getSimpleName());
		for (Treasure treasure : treasures) {
			treasureMap.put(treasure.getType(), treasure);
		}
		this.treasureMap = treasureMap;
	}

	/**
	 * @Title: pickHero
	 * @Description: 抽取英雄卡
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void pickHeroCard(ChannelHandlerContext ctx, ProtoMessage data) {
		int treasureId = data.getData().getIntValue("treasureid");
		long userid = data.getUserid();
		JSONArray ret = new JSONArray();
		Player player = ds.find(Player.class).field("_id")
				.equal(data.getUserid()).get();
		Map<Integer, TreasureInfo> treasures = player.getTreasures();
		if (!treasures.containsKey(Integer.valueOf(treasureId))) {
			logger.error("玩家{}打开宝箱失败，没有此宝箱", userid);
			HttpInHandler.writeJSON(ctx, ProtoMessage.getErrorResp("没有此宝箱"));
			return;
		}
		Treasure treasure = treasureMap.get(treasureId);
		int random1 = (int) Math.round(Math.random() * 100);
		int random2 = (int) Math.round(Math.random() * 100);
		int random3 = (int) Math.round(Math.random() * 100);
		if (random1 < treasurePropertyMap.get(1)) {
			// 获取英雄1
			int hero1 = treasure.getHero1();
			HeroMgr.getInstance().addHero(player, hero1, 1, userid);
			ret.add(hero1);
		}
		if (random2 < treasurePropertyMap.get(2)) {
			// 获取英雄2
			int hero2 = treasure.getHero1();
			HeroMgr.getInstance().addHero(player, hero2, 1, userid);
			ret.add(hero2);
		}
		if (random3 < treasurePropertyMap.get(3)) {
			// 获取英雄3
			int hero3 = treasure.getHero1();
			HeroMgr.getInstance().addHero(player, hero3, 1, userid);
			ret.add(hero3);
		}
		ds.save(player);
		HttpInHandler.writeJSON(ctx, ret);
	}

	/**
	 * @Title: pickTreasure
	 * @Description: 获取宝箱
	 * @param ctx
	 * @param userid
	 * @return void
	 * @throws
	 */
	public void pickTreasure(long userid) {
		int random = (int) Math.round(Math.random() * 100);
		int sum = 0;
		int type = 0;
		for (int i = 1; i <= propertyMap.keySet().size(); i++) {
			int property = propertyMap.get(i);
			if (random >= sum && random < sum + property) {
				type = i;
				break;
			}
			sum += property;
		}
		Player player = ds.find(Player.class).field("_id").equal(userid).get();
		TreasureInfo treasureInfo = new TreasureInfo();
		treasureInfo.setId(player.getTreasures().size() + 1);
		treasureInfo.setType(type);
		ds.save(player);
		logger.info("玩家{}抽取到宝箱{}", userid, type);
	}

	/**
	 * @Title: queryTreasure
	 * @Description: 查询宝箱
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void queryTreasure(ChannelHandlerContext ctx, ProtoMessage data) {
		long userid = data.getUserid();
		Player player = ds.find(Player.class).field("_id").equal(userid).get();
		JSONArray ret = new JSONArray();
		Map<Integer, TreasureInfo> treasureMap = player.getTreasures();
		for (Integer key : treasureMap.keySet()) {
			ret.add(treasureMap.get(key).getType());
		}
		HttpInHandler.writeJSON(ctx, ret);
	}
}
