package com.hjc.herolpvp.net;

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
	public static final short TEST = 10000;// 测试网络延迟
	public static final short EXIT_SCENE = 10002;// 退出场景
	public static final short FIGHT_ENTER_SCENE = 1;// 进入游戏场景
	public static final short FIGHT_SKILL = 2;// 释放技能
	public static final short FIGHT_PICK_HERO = 3;// 释放技能

	public static void regist(int protoId, String className) {
		protoClassMap.put(protoId, className);
		classProtoMap.put(className, protoId);
	}
}
