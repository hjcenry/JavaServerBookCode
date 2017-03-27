package com.hjc.herol.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: DailyTask
 * @Description: 每日定时任务
 * @author 何金成
 * @date 2016年3月30日 下午4:57:35
 * 
 */
public class DailyTaskService {
	public static Logger logger = LoggerFactory
			.getLogger(DailyTaskService.class);

	public void pkDailyAwardMail() {
		// TODO
		logger.info("每日定时发送邮件");
	}
}
