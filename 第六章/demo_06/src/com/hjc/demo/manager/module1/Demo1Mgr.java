package com.hjc.demo.manager.module1;

import com.alibaba.fastjson.JSONArray;
import com.hjc.demo.net.NetFramework;
import com.hjc.demo.net.ProtoMessage;
import com.hjc.test.Demo;

public class Demo1Mgr {
	private static Demo1Mgr demo1Mgr;

	private Demo1Mgr() {

	}

	public static Demo1Mgr getInstance() {
		if (demo1Mgr == null) {
			demo1Mgr = new Demo1Mgr();
		}
		return demo1Mgr;
	}

	public void initData() {
		Demo.print("Demp1Mgr initData");
	}

	public void demoLogic1(NetFramework net, ProtoMessage msg, long userid) {
		Demo.print("invoke demoLogic1");
		JSONArray ret = new JSONArray();
		ret.add(userid);
		ret.add(msg.getData());
		net.write(ret);
	}
}
