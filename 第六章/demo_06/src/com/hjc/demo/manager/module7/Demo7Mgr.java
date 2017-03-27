package com.hjc.demo.manager.module7;

import com.alibaba.fastjson.JSONArray;
import com.hjc.demo.manager.event.ED;
import com.hjc.demo.manager.event.Event;
import com.hjc.demo.manager.event.EventMgr;
import com.hjc.demo.manager.event.EventProc;
import com.hjc.demo.net.NetFramework;
import com.hjc.demo.net.ProtoMessage;
import com.hjc.test.Demo;

public class Demo7Mgr extends EventProc {
	private static Demo7Mgr demo7Mgr;

	private Demo7Mgr() {

	}

	public static Demo7Mgr getInstance() {
		if (demo7Mgr == null) {
			demo7Mgr = new Demo7Mgr();
		}
		return demo7Mgr;
	}

	public void initData() {
		Demo.print("Demp7Mgr initData");
		// Demo
	}

	public void demoLogic7(NetFramework net, ProtoMessage msg, long userid) {
		Demo.print("invoke demoLogic7");
		// 触发事件2
		EventMgr.addEvent(ED.EVE2, "事件2的参数");
		JSONArray ret = new JSONArray();
		ret.add(userid);
		ret.add(msg.getData());
		net.write(ret);
	}

	@Override
	public void proc(Event param) {
		if (param.id == ED.EVE2) {
			String msg = (String) param.param;
			Demo.print("invoke event2,param:" + msg);
		}
	}

	@Override
	protected void doReg() {
		EventMgr.regist(ED.EVE2, this);
	}
}
