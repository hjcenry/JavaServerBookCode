package com.hjc.herolpvp.manager.event;

/**
 * Event Id Definition
 * 
 * @author 何金成
 *
 */
public class ED {
	// 工人队列检查
	public static final int CHECK_WORKER_OPEN = 10001;
	// 添加背包道具
	public static final int BAG_ADD_ITEM = 10002;
	// 删除背包道具
	public static final int BAG_REM_ITEM = 10003;
	// 训练队列检查
	public static final int CHECK_TRAIN_OPEN = 10004;
	// 每日任务事件
	/**
	 * @Fields TASK_DAILY_JUN_LING : 每日军令
	 */
	public static final int TASK_DAILY_JUN_LING = 10005;
	/**
	 * @Fields TASK_DAILY_ZHENG_ZHAN : 征战四方
	 */
	public static final int TASK_DAILY_ZHENG_ZHAN = 10006;
	/**
	 * @Fields TASK_DAILY_EXCELLENCE_ZHENG_ZHAN : 征战精英
	 */
	public static final int TASK_DAILY_EXCELLENCE_ZHENG_ZHAN = 10007;
	/**
	 * @Fields TASK_DAILY_PVP : 经济挑战
	 */
	public static final int TASK_DAILY_PVP = 10008;
	/**
	 * @Fields TASK_DAILY_HCZS : 主城征收
	 */
	public static final int TASK_DAILY_ZCZS = 10009;
	/**
	 * @Fields TASK_DAILY_BUY_JUN_LING : 购买军令
	 */
	public static final int TASK_DAILY_BUY_JUN_LING = 10010;
	/**
	 * @Fields TASK_DAILY_PUB_CARD : 酒馆抽卡
	 */
	public static final int TASK_DAILY_PUB_CARD = 10011;
	/**
	 * @Fields TASK_DAILY_PUB_PICK_10 : 酒馆十连抽
	 */
	public static final int TASK_DAILY_PUB_PICK_10 = 10012;
	/**
	 * @Fields TASK_DAILY_PUB_COMDEPOSE : 分解卡牌
	 */
	public static final int TASK_DAILY_DECOMPOSE = 10013;
	// 主线任务事件
	/**
	 * @Fields TASK_MAIN_BUILDING : 建筑升级
	 */
	public static final int TASK_MAIN_BUILDING = 10014;
	/**
	 * @Fields TASK_MAIN_LEVEL : 君主升级
	 */
	public static final int TASK_MAIN_LEVEL = 10015;
	/**
	 * @Fields TASK_MAIN_CARD : 卡牌任务
	 */
	public static final int TASK_MAIN_CARD = 10016;
	/**
	 * @Fields TASK_MAIN_PVE : 推图任务
	 */
	public static final int TASK_MAIN_PVE = 10017;
	/**
	 * @Fields TASK_MAIN_PVE : 计算战力
	 */
	public static final int CAL_POWER = 10018;
	/**
	 * @Fields CAL_ALL_POWER : 计算出战战力
	 */
	public static final int CAL_ALL_POWER = 10019;
	/**
	 * @Fields CHECK_PK_OPEN : 检查竞技场开启
	 */
	public static final int CHECK_PK_OPEN = 10020;

}
