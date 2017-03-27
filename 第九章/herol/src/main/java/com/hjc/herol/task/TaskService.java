package com.hjc.herol.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DailyTask
 * @Description: 每日定时任务
 * @author 何金成
 * @date 2016年3月30日 下午4:57:35
 * 
 */
@Service
public class TaskService {
	@Autowired
	private TaskExecutor executor;
	public Logger logger = LoggerFactory.getLogger(TaskService.class);
	private static DailyTaskService dailyTaskService = new DailyTaskService();

	@Scheduled(cron = "0 0 22 * * ? ")
	public void pkDailyAwardMail() {
		executor.execute(new Runnable() {
			public void run() {
				dailyTaskService.pkDailyAwardMail();
			}
		});
	}
}
