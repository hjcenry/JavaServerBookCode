package com.hjc.demo.manager.module2;

import com.alibaba.fastjson.JSONArray;
import com.hjc.demo.net.NetFramework;
import com.hjc.demo.net.ProtoMessage;
import com.hjc.test.Demo;

public class Demo2Mgr {
	private static Demo2Mgr demo2Mgr;

	private Demo2Mgr() {

	}

	public static Demo2Mgr getInstance() {
		if (demo2Mgr == null) {
			demo2Mgr = new Demo2Mgr();
		}
		return demo2Mgr;
	}

	public void initData() {
		Demo.print("Demp2Mgr initData");
		// Demo
	}

	public void demoLogic2(NetFramework net, ProtoMessage msg, long userid) {
		Demo.print("invoke demoLogic2");
		JSONArray ret = new JSONArray();
		ret.add(userid);
		ret.add(msg.getData());
		net.write(ret);
	}
}
