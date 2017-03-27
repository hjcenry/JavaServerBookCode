package com.hjc.herol.manager.hero;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.hjc.herol.manager.player.Player;
import com.hjc.herol.net.ProtoMessage;
import com.hjc.herol.net.http.HttpInHandler;
import com.hjc.herol.template.Hero;
import com.hjc.herol.util.csv.TempletService;
import com.hjc.herol.util.mongo.MorphiaUtil;

public class HeroMgr {
	private static HeroMgr heroMgr;
	private static final Logger logger = LoggerFactory.getLogger(HeroMgr.class);
	private Datastore ds = MorphiaUtil.ds;
	public Map<Integer, Hero> heroMap = new HashMap<Integer, Hero>();
	public static final int GROUP_MAX = 8;// 卡组最大卡牌数量

	private HeroMgr() {
	}

	public static HeroMgr getInstance() {
		if (null == heroMgr) {
			heroMgr = new HeroMgr();
		}
		return heroMgr;
	}

	public void initData() {
		logger.info("HeroMgr initData");
	}

	public void initCsvData() {
		logger.info("HeroMgr initCsvData");
		// 加载英雄数据表，英雄数据读取自策划配置的CSV数据表（这里是由我这个不大懂策划的人配置的，所以数据不必在意，主要介绍数据加载的方式）
		Map<Integer, Hero> heroMap = new HashMap<Integer, Hero>();
		List<Hero> heros = TempletService.listAll(Hero.class.getSimpleName());
		for (Hero hero : heros) {
			heroMap.put(hero.getHeroId(), hero);
		}
		this.heroMap = heroMap;
	}

	/**
	 * @Title: addHero
	 * @Description: 添加英雄
	 * @param heroId
	 * @param count
	 * @param userid
	 * @return void
	 * @throws
	 */
	public void addHero(Player player, int heroId, int count, long userid) {
		for (int i = 0; i < count; i++) {
			HeroInfo heroInfo = new HeroInfo();
			heroInfo.setLevel(1);
			heroInfo.setHeroId(heroId);
			heroInfo.setGroupPosition(0);
			heroInfo.setFightGroup(0);
			heroInfo.setCount(!player.getHeros().containsKey(
					Integer.valueOf(heroId)) ? 0 : player.getHeros()
					.get(heroId).getCount());
			player.getHeros().put(heroInfo.getHeroId(), heroInfo);
		}
	}

	/**
	 * @Title: setHeroGroup
	 * @Description: 设置卡牌卡组
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void setHeroGroup(ChannelHandlerContext ctx, ProtoMessage data) {
		long userid = data.getUserid();
		int heroId = data.getData().getIntValue("heroid");
		int group = data.getData().getIntValue("group");
		int groupPos = data.getData().getIntValue("grouppos");
		Player player = ds.find(Player.class).field("_id").equal(userid).get();
		Map<Integer, HeroInfo> heros = player.getHeros();
		HeroInfo hero = heros.get(heroId);
		if (getHeroByGroup(heros, group).size() >= GROUP_MAX) {
			logger.error("玩家{}设置卡组失败，卡组达到最大数量");
			HttpInHandler.writeJSON(ctx, ProtoMessage.getErrorResp("卡组达到最大数量"));
			return;
		}
		hero.setFightGroup(group);
		HeroInfo oldHero = getHeroByGroupPos(heros, groupPos);
		if (oldHero == null) {
			hero.setGroupPosition(groupPos);
		} else {
			// 位置上有卡牌。替换到未出战卡组
			oldHero.setGroupPosition(0);
			oldHero.setFightGroup(0);
			heros.put(oldHero.getHeroId(), oldHero);
		}
		heros.put(heroId, hero);
		ds.save(player);
		HttpInHandler.writeJSON(ctx, ProtoMessage.getSuccessResp());
	}

	/**
	 * @Title: getHeroByGroupPos
	 * @Description: 根据卡组位置获取英雄
	 * @param heros
	 * @param groupPos
	 * @return
	 * @return HeroInfo
	 * @throws
	 */
	public HeroInfo getHeroByGroupPos(Map<Integer, HeroInfo> heros, int groupPos) {
		for (Integer key : heros.keySet()) {
			if (heros.get(key).getGroupPosition() == groupPos) {
				return heros.get(key);
			}
		}
		return null;
	}

	/**
	 * @Title: upHeroLevel
	 * @Description: 升级英雄卡
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void upHeroLevel(ChannelHandlerContext ctx, ProtoMessage data) {
		long userid = data.getUserid();
		int heroId = data.getData().getIntValue("heroid");
		Player player = ds.find(Player.class).field("_id").equal(userid).get();
		Map<Integer, HeroInfo> heros = player.getHeros();
		if (!heros.containsKey(Integer.valueOf(heroId))) {
			logger.error("玩家{}升级英雄失败，没有英雄卡牌", userid);
			HttpInHandler.writeJSON(ctx, ProtoMessage.getErrorResp("没有英雄卡牌"));
			return;
		}
		HeroInfo hero = heros.get(heroId);
		if (player.coin < 50 * hero.getLevel()) {
			logger.error("玩家{}升级英雄失败，金币不足", userid);
			HttpInHandler.writeJSON(ctx, ProtoMessage.getErrorResp("金币不足"));
			return;
		}
		if (hero.getCount() <= hero.getLevel() * 5) {
			logger.error("玩家{}升级英雄失败，英雄数量不足", userid);
			HttpInHandler.writeJSON(ctx, ProtoMessage.getErrorResp("英雄数量不足"));
			return;
		}
		hero.setLevel(hero.getLevel() + 1);
		hero.setCount(hero.getCount() - hero.getLevel() * 5);
		player.coin -= 50 * hero.getLevel();
		ds.save(player);
		HttpInHandler.writeJSON(ctx, ProtoMessage.getSuccessResp());
	}

	/**
	 * @Title: queryHero
	 * @Description: 查询英雄卡牌
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void queryHero(ChannelHandlerContext ctx, ProtoMessage data) {
		long userid = data.getUserid();
		int group = data.getData().getIntValue("group");// 要查询得出战卡组
		JSONArray ret = new JSONArray();
		Player player = ds.find(Player.class).field("_id").equal(userid).get();
		Map<Integer, HeroInfo> heros = player.getHeros();
		ret.add(getHeroByGroup(heros, group));
		ret.add(getHeroByGroup(heros, 0));
		HttpInHandler.writeJSON(ctx, ret);
	}

	/**
	 * @Title: getHeroByGroup
	 * @Description: 获取卡组的卡牌
	 * @param group
	 * @param userid
	 * @return
	 * @return List<Integer>
	 * @throws
	 */
	public JSONArray getHeroByGroup(Map<Integer, HeroInfo> heros, int group) {
		JSONArray heroRet = new JSONArray();
		for (Integer key : heros.keySet()) {
			if (group == heros.get(key).getFightGroup()) {
				heroRet.add(heros.get(key).getHeroId());
				heroRet.add(heros.get(key).getLevel());
				heroRet.add(heros.get(key).getGroupPosition());
			}
		}
		return heroRet;
	}

}
