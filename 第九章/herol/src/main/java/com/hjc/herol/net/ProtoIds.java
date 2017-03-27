package com.hjc.herol.net;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: ProtoIds
 * @Description: 存储协议号，添加协议号时，既要定义静态常量的协议号，也要在init方法中调用regist注册，方便协议号的管理
 * @author 何金成
 * @date 2015年5月23日 下午4:34:41
 * 
 */
public class ProtoIds {
	public static Map<Integer, String> protoClassMap = new HashMap<Integer, String>();// 协议号-类名map存储
	public static Map<String, Integer> classProtoMap = new HashMap<String, Integer>();// 类名-协议号map存储

	// 初始化
	public static void init() {
		// 协议初始化
		// regist(C_LOGIN_REQ, LoginReq.class.getName());
		// regist(S_LOGIN_RESP, LoginResp.class.getName());
	}

	/** 登录请求 **/
	public static final short TEST = 10000;
	public static final short GET_PVP_SERVER = 10001;// 获取Pvp服务器信息
	public static final short CREATE_ROLE = 1;// 创建角色
	public static final short PLAYER_QUERY = 2;// 查询玩家信息
	public static final short PLAYER_SET_GROUP = 3;// 设置出战卡组
	public static final short HERO_SET_GROUP = 4;// 设置英雄所属卡组
	public static final short HERO_QUERY = 5;// 查询英雄
	public static final short HERO_UP_LEVEL = 6;// 升级英雄
	public static final short TREASURE_QUERY = 7;// 查询宝箱
	public static final short TREASURE_HERO_PICK = 8;// 抽取英雄

	public static void regist(int protoId, String className) {
		protoClassMap.put(protoId, className);
		classProtoMap.put(className, protoId);
	}
}
