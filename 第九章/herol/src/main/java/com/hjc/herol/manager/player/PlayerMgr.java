package com.hjc.herol.manager.player;

import java.util.HashMap;

import io.netty.channel.ChannelHandlerContext;

import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.hjc.herol.manager.hero.HeroInfo;
import com.hjc.herol.manager.hero.HeroMgr;
import com.hjc.herol.manager.treasure.TreasureInfo;
import com.hjc.herol.net.ProtoMessage;
import com.hjc.herol.net.http.HttpInHandler;
import com.hjc.herol.util.mongo.MorphiaUtil;

public class PlayerMgr {
	private static PlayerMgr junZhuMgr;
	private static final Logger logger = LoggerFactory
			.getLogger(PlayerMgr.class);
	private Datastore ds = MorphiaUtil.ds;

	private PlayerMgr() {
	}

	public static PlayerMgr getInstance() {
		if (null == junZhuMgr) {
			junZhuMgr = new PlayerMgr();
		}
		return junZhuMgr;
	}

	public void initData() {
		logger.info("JunZhuMgr initData");
	}

	public void initCsvData() {
		logger.info("JunZhuMgr initCsvData");
	}

	/**
	 * @Title: createRole
	 * @Description: 创建角色
	 * @param ctx
	 * @param data
	 * @param userid
	 * @return void
	 * @throws
	 */
	public void createRole(ChannelHandlerContext ctx, ProtoMessage data) {
		Player player = ds.find(Player.class).field("_id")
				.equal(data.getUserid()).get();
		if (player != null) {
			logger.error("角色已创建");
			HttpInHandler.writeJSON(ctx, ProtoMessage.getErrorResp("角色已创建"));
			return;
		}
		player = new Player();
		player._id = data.getUserid();
		player.name = data.getData().getString("name");
		// 初始化金币1000
		player.coin = 1000;
		// 初始化元宝100
		player.yuanbao = 100;
		// 初始化0杯
		player.cups = 0;
		player.heros = new HashMap<Integer, HeroInfo>();
		player.treasures = new HashMap<Integer, TreasureInfo>();
		// 默认添加三张英雄卡牌
		HeroMgr.getInstance().addHero(player, 1, 1, data.getUserid());
		HeroMgr.getInstance().addHero(player, 2, 1, data.getUserid());
		HeroMgr.getInstance().addHero(player, 3, 1, data.getUserid());
		ds.save(player);
		HttpInHandler.writeJSON(ctx, ProtoMessage.getSuccessResp());
	}

	/**
	 * @Title: queryPlayer
	 * @Description: 查询玩家所有信息
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void queryPlayer(ChannelHandlerContext ctx, ProtoMessage data) {
		Player player = ds.find(Player.class).field("_id")
				.equal(data.getUserid()).get();
		JSONArray ret = new JSONArray();
		ret.add(player._id);
		ret.add(player.name);
		ret.add(player.coin);
		ret.add(player.yuanbao);
		ret.add(player.fightGroup);
		ret.add(player.cups);
		// 添加英雄
		JSONArray heros = new JSONArray();
		for (Integer key : player.getHeros().keySet()) {
			JSONArray hero = new JSONArray();
			hero.add(player.getHeros().get(key).getHeroId());
			hero.add(player.getHeros().get(key).getLevel());
			hero.add(player.getHeros().get(key).getCount());
			hero.add(player.getHeros().get(key).getFightGroup());
			hero.add(player.getHeros().get(key).getGroupPosition());
			heros.add(hero);
		}
		ret.add(heros);
		// 添加宝箱
		JSONArray treasures = new JSONArray();
		if (player.getTreasures() != null) {
			for (Integer key : player.getTreasures().keySet()) {
				JSONArray treasure = new JSONArray();
				treasure.add(player.getTreasures().get(key).getType());
				treasures.add(treasure);
			}
		}
		ret.add(treasures);
		HttpInHandler.writeJSON(ctx, ret);
	}

	/**
	 * @Title: setFightGroup
	 * @Description: 设置出战卡组
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void setFightGroup(ChannelHandlerContext ctx, ProtoMessage data) {
		long userid = data.getUserid();
		int fightGroup = data.getData().getIntValue("group");
		Player player = ds.find(Player.class).field("_id").equal(userid).get();
		player.fightGroup = fightGroup;
		ds.save(player);
		HttpInHandler.writeJSON(ctx, ProtoMessage.getSuccessResp());
	}

}
