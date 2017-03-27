package com.hjc.demo.manager.module9;

import com.alibaba.fastjson.JSONArray;
import com.hjc.demo.manager.event.ED;
import com.hjc.demo.manager.event.Event;
import com.hjc.demo.manager.event.EventMgr;
import com.hjc.demo.manager.event.EventProc;
import com.hjc.demo.net.NetFramework;
import com.hjc.demo.net.ProtoMessage;
import com.hjc.test.Demo;

public class Demo9Mgr extends EventProc {
	private static Demo9Mgr demo9Mgr;

	private Demo9Mgr() {

	}

	public static Demo9Mgr getInstance() {
		if (demo9Mgr == null) {
			demo9Mgr = new Demo9Mgr();
		}
		return demo9Mgr;
	}

	public void initData() {
		Demo.print("Demp9Mgr initData");
		// Demo
	}

	public void demoLogic9(NetFramework net, ProtoMessage msg, long userid) {
		Demo.print("invoke demoLogic9");
		// 触发事件4
		EventMgr.addEvent(ED.EVE4, new Object[] { 4, "事件4的参数" });
		JSONArray ret = new JSONArray();
		ret.add(userid);
		ret.add(msg.getData());
		net.write(ret);
	}

	@Override
	public void proc(Event param) {
		if (param.id == ED.EVE4) {
			Object[] objs = (Object[]) param.param;
			int param1 = (Integer) objs[0];
			String param2 = (String) objs[1];
			Demo.print("invoke event4,param1:" + param1 + ",param2:" + param2);
		}
	}

	@Override
	protected void doReg() {
		EventMgr.regist(ED.EVE4, this);
	}
}
