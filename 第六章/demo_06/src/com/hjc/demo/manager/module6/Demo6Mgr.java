package com.hjc.demo.manager.module6;

import com.alibaba.fastjson.JSONArray;
import com.hjc.demo.manager.event.ED;
import com.hjc.demo.manager.event.Event;
import com.hjc.demo.manager.event.EventMgr;
import com.hjc.demo.manager.event.EventProc;
import com.hjc.demo.net.NetFramework;
import com.hjc.demo.net.ProtoMessage;
import com.hjc.test.Demo;

public class Demo6Mgr extends EventProc {
	private static Demo6Mgr demo6Mgr;

	private Demo6Mgr() {

	}

	public static Demo6Mgr getInstance() {
		if (demo6Mgr == null) {
			demo6Mgr = new Demo6Mgr();
		}
		return demo6Mgr;
	}

	public void initData() {
		Demo.print("Demp6Mgr initData");
		// Demo
	}

	public void demoLogic6(NetFramework net, ProtoMessage msg, long userid) {
		Demo.print("invoke demoLogic6");
		// 触发事件1
		EventMgr.addEvent(ED.EVE1, userid);
		JSONArray ret = new JSONArray();
		ret.add(userid);
		ret.add(msg.getData());
		net.write(ret);
	}

	@Override
	public void proc(Event param) {
		if (param.id == ED.EVE1) {
			Long userid = (Long) param.param;
			Demo.print("invoke event1,param:" + userid);
		}
	}

	@Override
	protected void doReg() {
		// 实现注册事件
		EventMgr.regist(ED.EVE1, this);
	}
}
