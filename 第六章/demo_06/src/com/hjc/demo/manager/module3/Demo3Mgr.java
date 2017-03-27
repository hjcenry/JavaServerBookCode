package com.hjc.demo.manager.module3;

import com.alibaba.fastjson.JSONArray;
import com.hjc.demo.net.NetFramework;
import com.hjc.demo.net.ProtoMessage;
import com.hjc.test.Demo;

public class Demo3Mgr {
	private static Demo3Mgr demo3Mgr;

	private Demo3Mgr() {

	}

	public static Demo3Mgr getInstance() {
		if (demo3Mgr == null) {
			demo3Mgr = new Demo3Mgr();
		}
		return demo3Mgr;
	}

	public void initData() {
		Demo.print("Demp3Mgr initData");
		// Demo
	}

	public void demoLogic3(NetFramework net, ProtoMessage msg, long userid) {
		Demo.print("invoke demoLogic3");
		JSONArray ret = new JSONArray();
		ret.add(userid);
		ret.add(msg.getData());
		net.write(ret);
	}
}
