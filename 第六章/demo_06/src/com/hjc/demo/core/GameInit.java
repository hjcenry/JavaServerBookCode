package com.hjc.demo.core;

import com.hjc.demo.manager.event.EventMgr;
import com.hjc.demo.net.NetModule;
import com.hjc.demo.task.JobMgr;
import com.hjc.demo.task.QuartzManager;
import com.hjc.demo.util.ExecutorPool;
import com.hjc.test.Demo;

/**
 * @ClassName: GameInit
 * @Description: 启动类
 * @author 何金成
 * @date 2016年4月27日 上午11:57:05
 * 
 */
public class GameInit {
	public static volatile int state = 0;// 服务器状态：0-关闭，1-开启

	public static boolean start() {
		Demo.print("启动逻辑服务器...");
		GameInit.state = 1;
		Demo.print("启动网络模块");
		NetModule.getInstance().startNet();
		Demo.print("初始化线程池");
		ExecutorPool.initThreadsExecutor();
		Demo.print("启动事件处理器");
		EventMgr.getInstance().init();
		Demo.print("初始化消息路由和功能模块");
		Router.getInstance().initData();
		Demo.print("开启定时任务调度");
		JobMgr.getInstance().initJobs();
		return true;
	}

	public static boolean shut() {
		Demo.print("关闭逻辑服务器...");
		GameInit.state = 0;
		Demo.print("关闭网络模块");
		NetModule.getInstance().shutNet();
		Demo.print("关闭定时任务调度");
		JobMgr.getInstance().stopJobs();
		Demo.print("关闭事件处理器");
		EventMgr.shutdown();
		return true;
	}
}
