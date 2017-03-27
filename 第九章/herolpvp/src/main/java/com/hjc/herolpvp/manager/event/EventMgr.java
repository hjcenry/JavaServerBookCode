package com.hjc.herolpvp.manager.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务器内部事件处理器；用于断开模块之间耦合。 比如登录后要给好友发上线通知，则登录完成后触发一个登录事件， 关心这个事件的模块，去处理自己的业务；
 * 避免在登录完成后直接去调用其他模块的接口。
 * 
 * @author 何金成
 *
 */
public class EventMgr implements Runnable {
	public static Logger log = LoggerFactory.getLogger(EventMgr.class);
	public static Map<Integer, List<EventProc>> procs = new HashMap<Integer, List<EventProc>>();
	public static BlockingQueue<Event> queue = new LinkedBlockingQueue<Event>();
	public volatile boolean work;
	public static EventMgr inst;
	private static Event exit = new Event();

	public EventMgr() {
	}

	public static EventMgr getInstance() {
		if (inst == null) {
			inst = new EventMgr();
		}
		return inst;
	}

	public void init() {
		work = true;
		new Thread(this, "EventMgr").start();
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
				log.error("Exception when take", evt);
				continue;
			}
			if (evt == exit) {
				break;
			}
			List<EventProc> list = procs.get(evt.id);
			if (list == null) {
				log.info("该事件id：{},没有事件注册过", evt.id);
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
					log.error("处理事件异常 {} {} proc {}", evt.id, evt.param, proc
							.getClass().getSimpleName());
					log.error("异常内容", t);
				}
			}
		}
		log.info("退出事件处理器 ");
	}

	public static void regist(int id, EventProc proc) {
		List<EventProc> list = procs.get(id);
		if (list == null) {
			list = new ArrayList<EventProc>(1);
			procs.put(id, list);
		}
		list.add(proc);
		/*
		 * for (Integer key : procs.keySet()) { System.out.println("Event Key:"
		 * + key); }
		 */
	}

	public static void sendEventFinishMessage() {

	}
}
