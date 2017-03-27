package com.hjc.demo.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: ExecutorPool
 * @Description: 线程池管理
 * @author 何金成
 * @date 2016年4月18日 下午3:42:42
 * 
 */
public class ExecutorPool {
	public static ExecutorService handleThreadPool = null;

	public static void initThreadsExecutor() {
		handleThreadPool = Executors.newCachedThreadPool();
	}

	public static void execute(Runnable runnable) {
		handleThreadPool.execute(runnable);
	}

	public static void shutdown() {
		if (!handleThreadPool.isShutdown())
			handleThreadPool.shutdown();
	}
}
