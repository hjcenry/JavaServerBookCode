package com.hjc.demo.manager.module8;

import com.alibaba.fastjson.JSONArray;
import com.hjc.demo.manager.event.ED;
import com.hjc.demo.manager.event.Event;
import com.hjc.demo.manager.event.EventMgr;
import com.hjc.demo.manager.event.EventProc;
import com.hjc.demo.net.NetFramework;
import com.hjc.demo.net.ProtoMessage;
import com.hjc.test.Demo;

public class Demo8Mgr extends EventProc {
	private static Demo8Mgr demo8Mgr;

	private Demo8Mgr() {

	}

	public static Demo8Mgr getInstance() {
		if (demo8Mgr == null) {
			demo8Mgr = new Demo8Mgr();
		}
		return demo8Mgr;
	}

	public void initData() {
		Demo.print("Demp8Mgr initData");
		// Demo
	}

	public void demoLogic8(NetFramework net, ProtoMessage msg, long userid) {
		Demo.print("invoke demoLogic8");
		DemoBean bean = new DemoBean();
		bean.setId(1001l);
		bean.setMsg("事件3的参数");
		// 触发事件3
		EventMgr.addEvent(ED.EVE3, bean);
		JSONArray ret = new JSONArray();
		ret.add(userid);
		ret.add(msg.getData());
		net.write(ret);
	}

	@Override
	public void proc(Event param) {
		if (param.id == ED.EVE3) {
			DemoBean bean = (DemoBean) param.param;
			Demo.print("invoke event3,param:javabean:id:" + bean.getId()
					+ ",msg:" + bean.getMsg());
		}
	}

	@Override
	protected void doReg() {
		EventMgr.regist(ED.EVE3, this);
	}
}
