package com.hjc.demo.task;

import java.text.ParseException;

import org.quartz.SchedulerException;

import com.hjc.test.Demo;

/**
 * @ClassName: JobMgr
 * @Description: Job管理类
 * @author 何金成
 * @date 2016年4月28日 下午10:23:51
 * 
 */
public class JobMgr {
	private static JobMgr jobMgr;
	private TestJob job = new TestJob();

	private JobMgr() {

	}

	public static JobMgr getInstance() {
		if (jobMgr == null) {
			jobMgr = new JobMgr();
		}
		return jobMgr;
	}

	/**
	 * @Title: initJobs
	 * @Description: 初始化所有任务
	 * @return void
	 * @throws
	 */
	public void initJobs() {
		try {
			// 添加Job定时任务
			QuartzManager.addJob(TestJob.class.getSimpleName(), job,
					"0/2 * * * * ?");
		} catch (SchedulerException | ParseException e) {
			e.printStackTrace();
		}
		Demo.print("开启定时任务成功");
	}

	/**
	 * @Title: stopJobs
	 * @Description: 关闭所有的任务
	 * @return void
	 * @throws
	 */
	public void stopJobs() {
		try {
			QuartzManager.removeJob(TestJob.class.getSimpleName());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
