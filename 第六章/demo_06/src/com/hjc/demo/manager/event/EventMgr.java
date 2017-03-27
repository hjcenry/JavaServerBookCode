package com.hjc.demo.manager.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.hjc.test.Demo;

/**
 * 服务器内部事件处理器；用于断开模块之间耦合。 比如登录后要给好友发上线通知，则登录完成后触发一个登录事件， 关心这个事件的模块，去处理自己的业务；
 * 避免在登录完成后直接去调用其他模块的接口。
 * 
 * @author 何金成
 *
 */
public class EventMgr implements Runnable {
	public static Map<Integer, List<EventProc>> procs;
	public static BlockingQueue<Event> queue = new LinkedBlockingQueue<Event>();
	public volatile boolean work;
	public static EventMgr inst;
	private static Event exit = new Event();

	private EventMgr() {
		inst = this;
	}

	public static EventMgr getInstance() {
		if (inst == null) {
			inst = new EventMgr();
		}
		return inst;
	}

	public void init() {
		procs = new HashMap<Integer, List<EventProc>>();
		work = true;
		new Thread(this, "EventMgr").start();
		Demo.print("事件处理器启动成功");
	}

	public static void shutdown() {
		queue.add(exit);
	}

	public static void addEvent(int id, Object param) {
		Event evt = new Event();
		evt.id = id;
		evt.param = param;
		queue.add(evt);
	}

	@Override
	public void run() {
		while (work) {
			Event evt = null;
			try {
				evt = queue.take();
			} catch (InterruptedException e) {
				Demo.print("Exception when take" + evt);
				continue;
			}
			if (evt == exit) {
				break;
			}
			List<EventProc> list = procs.get(evt.id);
			if (list == null) {
				Demo.print("该事件id：" + evt.id + ",没有事件注册过");
				continue;
			}
			int cnt = list.size();
			for (int i = 0; i < cnt; i++) {
				EventProc proc = list.get(i);
				if (proc.disable) {
					continue;
				}
				try {
					proc.proc(evt);
				} catch (Throwable t) {
					Demo.print("处理事件异常 " + evt.id + " " + evt.param + " proc "
							+ proc.getClass().getSimpleName() + "");
					Demo.print("异常内容" + t);
				}
			}
		}
		Demo.print("退出事件处理器 ");
	}

	public static void regist(int id, EventProc proc) {
		List<EventProc> list = procs.get(id);
		if (list == null) {
			list = new ArrayList<EventProc>(1);
			procs.put(id, list);
		}
		list.add(proc);
	}

	public static void sendEventFinishMessage() {

	}
}
