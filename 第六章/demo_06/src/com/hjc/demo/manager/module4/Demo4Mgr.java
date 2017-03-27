package com.hjc.demo.manager.module4;

import com.alibaba.fastjson.JSONArray;
import com.hjc.demo.net.NetFramework;
import com.hjc.demo.net.ProtoMessage;
import com.hjc.test.Demo;

public class Demo4Mgr {
	private static Demo4Mgr demo4Mgr;

	private Demo4Mgr() {

	}

	public static Demo4Mgr getInstance() {
		if (demo4Mgr == null) {
			demo4Mgr = new Demo4Mgr();
		}
		return demo4Mgr;
	}

	public void initData() {
		Demo.print("Demp4Mgr initData");
		// Demo
	}

	public void demoLogic4(NetFramework net, ProtoMessage msg, long userid) {
		Demo.print("invoke demoLogic4");
		JSONArray ret = new JSONArray();
		ret.add(userid);
		ret.add(msg.getData());
		net.write(ret);
	}
}
