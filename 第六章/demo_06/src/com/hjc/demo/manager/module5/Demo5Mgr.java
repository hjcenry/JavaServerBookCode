package com.hjc.demo.manager.module5;

import com.alibaba.fastjson.JSONArray;
import com.hjc.demo.net.NetFramework;
import com.hjc.demo.net.ProtoMessage;
import com.hjc.test.Demo;

public class Demo5Mgr {
	private static Demo5Mgr demo5Mgr;

	private Demo5Mgr() {

	}

	public static Demo5Mgr getInstance() {
		if (demo5Mgr == null) {
			demo5Mgr = new Demo5Mgr();
		}
		return demo5Mgr;
	}

	public void initData() {
		Demo.print("Demp5Mgr initData");
		// Demo
	}

	public void demoLogic5(NetFramework net, ProtoMessage msg, long userid) {
		Demo.print("invoke demoLogic5");
		JSONArray ret = new JSONArray();
		ret.add(userid);
		ret.add(msg.getData());
		net.write(ret);
	}
}
