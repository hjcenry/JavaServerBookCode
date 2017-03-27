package com.hjc.demo.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjc.demo.manager.module1.Demo1Mgr;
import com.hjc.demo.manager.module2.Demo2Mgr;
import com.hjc.demo.manager.module3.Demo3Mgr;
import com.hjc.demo.manager.module4.Demo4Mgr;
import com.hjc.demo.manager.module5.Demo5Mgr;
import com.hjc.demo.manager.module6.Demo6Mgr;
import com.hjc.demo.manager.module7.Demo7Mgr;
import com.hjc.demo.manager.module8.Demo8Mgr;
import com.hjc.demo.manager.module9.Demo9Mgr;
import com.hjc.demo.net.NetFramework;
import com.hjc.demo.net.ProtoIds;
import com.hjc.demo.net.ProtoMessage;
import com.hjc.test.Demo;

/** 
* @ClassName: Router 
* @Description: 游戏逻辑处理路由类
* @author 何金成
* @date 2016年5月2日 下午8:46:16 
*  
*/
public class Router {
	private static Router router;
	public Demo1Mgr demo1Mgr;
	public Demo2Mgr demo2Mgr;
	public Demo3Mgr demo3Mgr;
	public Demo4Mgr demo4Mgr;
	public Demo5Mgr demo5Mgr;
	public Demo6Mgr demo6Mgr;
	public Demo7Mgr demo7Mgr;
	public Demo8Mgr demo8Mgr;
	public Demo9Mgr demo9Mgr;

	private Router() {
		demo1Mgr = Demo1Mgr.getInstance();
		demo2Mgr = Demo2Mgr.getInstance();
		demo3Mgr = Demo3Mgr.getInstance();
		demo4Mgr = Demo4Mgr.getInstance();
		demo5Mgr = Demo5Mgr.getInstance();
		demo6Mgr = Demo6Mgr.getInstance();
		demo7Mgr = Demo7Mgr.getInstance();
		demo8Mgr = Demo8Mgr.getInstance();
		demo9Mgr = Demo9Mgr.getInstance();
	}

	public static Router getInstance() {
		if (router == null) {
			router = new Router();
		}
		return router;
	}

	public void initData() {
		Demo.print("Router initData");
		demo1Mgr.initData();
		demo2Mgr.initData();
		demo3Mgr.initData();
		demo4Mgr.initData();
		demo5Mgr.initData();
		demo6Mgr.initData();
		demo7Mgr.initData();
		demo8Mgr.initData();
		demo9Mgr.initData();
	}

	public void handle(NetFramework net, JSONObject req) throws Exception {
		// 传递数据按照json格式，json中需要包含ProtoMessage类中的字段
		if (!req.containsKey("protoid")) {
			NetFramework.inst.write(JSON.parseObject(JSON
					.toJSONString(ProtoMessage.getErrorResp("没有协议号"))));
			return;
		}
		int protoId = req.getIntValue("protoid");
		ProtoMessage reqMsg = JSON.parseObject(req.toJSONString(),
				ProtoMessage.class);
		if (!req.containsKey("userid")) {
			NetFramework.inst.write(JSON.parseObject(JSON
					.toJSONString(ProtoMessage.getErrorResp("没有userid"))));
			return;
		}
		long userid = reqMsg.getUserid();
		switch (protoId) {
		case ProtoIds.TEST:
			JSONArray ret = new JSONArray();
			ret.add("测试接口");
			NetFramework.inst.write(ret);
			break;
		case ProtoIds.DEMO_LOGIC_1:
			demo1Mgr.demoLogic1(net, reqMsg, userid);
			break;
		case ProtoIds.DEMO_LOGIC_2:
			demo2Mgr.demoLogic2(net, reqMsg, userid);
			break;
		case ProtoIds.DEMO_LOGIC_3:
			demo3Mgr.demoLogic3(net, reqMsg, userid);
			break;
		case ProtoIds.DEMO_LOGIC_4:
			demo4Mgr.demoLogic4(net, reqMsg, userid);
			break;
		case ProtoIds.DEMO_LOGIC_5:
			demo5Mgr.demoLogic5(net, reqMsg, userid);
			break;
		case ProtoIds.DEMO_LOGIC_6:
			demo6Mgr.demoLogic6(net, reqMsg, userid);
			break;
		case ProtoIds.DEMO_LOGIC_7:
			demo7Mgr.demoLogic7(net, reqMsg, userid);
			break;
		case ProtoIds.DEMO_LOGIC_8:
			demo8Mgr.demoLogic8(net, reqMsg, userid);
			break;
		case ProtoIds.DEMO_LOGIC_9:
			demo9Mgr.demoLogic9(net, reqMsg, userid);
			break;
		default:
			NetFramework.inst.write(JSON.parseObject(JSON
					.toJSONString(ProtoMessage
							.getErrorResp("未注册的协议号" + protoId))));
			break;
		}
	}
}
