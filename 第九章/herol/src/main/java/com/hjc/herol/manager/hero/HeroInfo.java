package com.hjc.herol.manager.hero;

public class HeroInfo {
	private int heroId;
	private int level;// 卡牌等级
	private int fightGroup;// 出战卡组 0-未出战，1-1卡组，2-2卡组，3-3卡组
	private int groupPosition;// 卡组位置
	private int count;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getFightGroup() {
		return fightGroup;
	}

	public void setFightGroup(int fightGroup) {
		this.fightGroup = fightGroup;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public int getGroupPosition() {
		return groupPosition;
	}

	public void setGroupPosition(int groupPosition) {
		this.groupPosition = groupPosition;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
