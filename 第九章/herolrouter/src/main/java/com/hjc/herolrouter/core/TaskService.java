package com.hjc.herolrouter.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



/**
 * 
 * <strong>Title : TaskService </strong>. <br>
 * <strong>Description : 定时任务服务.</strong> <br>
 * 
 */
@Component
public class TaskService {

	@Autowired
	private TaskExecutor taskExecutor;

	/**
	 * 并发处理线程数
	 */
	private static int threadNum = 100;

	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

	/**
	 * 测试
	 * */
	@Scheduled(cron = "0 */5 * * * *")
	public void testTask() {
		taskExecutor.execute(new Runnable() {
			public void run() {
				// Test Code
				// logger.info("Task Test");
			}
		});
	}

}
