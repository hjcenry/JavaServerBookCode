package com.hjc.herol.manager.player;

import java.util.Map;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.hjc.herol.manager.hero.HeroInfo;
import com.hjc.herol.manager.treasure.TreasureInfo;

@Entity
public class Player {
	@Id
	public long _id;// —用户id
	public String name;// — 用户名
	public int coin;// —用户金币
	public int yuanbao;// —用户元宝
	public int fightGroup;// 出战卡组
	public int cups;// 杯数
	public Map<Integer, HeroInfo> heros;// 拥有的英雄
	public Map<Integer, TreasureInfo> treasures;// 拥有的宝箱

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getYuanbao() {
		return yuanbao;
	}

	public void setYuanbao(int yuanbao) {
		this.yuanbao = yuanbao;
	}

	public Map<Integer, HeroInfo> getHeros() {
		return heros;
	}

	public void setHeros(Map<Integer, HeroInfo> heros) {
		this.heros = heros;
	}

	public Map<Integer, TreasureInfo> getTreasures() {
		return treasures;
	}

	public void setTreasures(Map<Integer, TreasureInfo> treasures) {
		this.treasures = treasures;
	}

	public int getCups() {
		return cups;
	}

	public void setCups(int cups) {
		this.cups = cups;
	}

}
