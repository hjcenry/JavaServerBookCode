package com.hjc.demo.task;

import java.text.ParseException;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @ClassName: QuartzManager
 * @Description: Quartz管理类
 * @author 何金成
 * @date 2016年4月28日 下午10:09:47
 * 
 */
public class QuartzManager {
	private static SchedulerFactory sf = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "group1";
	private static String TRIGGER_GROUP_NAME = "trigger1";

	/**
	 * @Title: addJob
	 * @Description: 添加Job
	 * @param jobName
	 * @param job
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 * @return void
	 * @throws
	 */
	public static void addJob(String jobName, Job job, String time)
			throws SchedulerException, ParseException {
		Scheduler sched = sf.getScheduler();
		JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME,
				job.getClass());// 任务名，任务组，任务执行类
		// 触发器
		CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);// 触发器名,触发器组
		trigger.setCronExpression(time);// 触发器时间设定
		sched.scheduleJob(jobDetail, trigger);
		// 启动
		if (!sched.isShutdown())
			sched.start();
	}

	/**
	 * @Title: addJob
	 * @Description: 添加Job
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @param job
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 * @return void
	 * @throws
	 */
	public static void addJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName, Job job, String time)
			throws SchedulerException, ParseException {
		Scheduler sched = sf.getScheduler();
		JobDetail jobDetail = new JobDetail(jobName, jobGroupName,
				job.getClass());// 任务名，任务组，任务执行类
		// 触发器
		CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组
		trigger.setCronExpression(time);// 触发器时间设定
		sched.scheduleJob(jobDetail, trigger);
		if (!sched.isShutdown())
			sched.start();
	}

	/**
	 * @Title: modifyJobTime
	 * @Description: 修改Job触发时间
	 * @param jobName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 * @return void
	 * @throws
	 */
	public static void modifyJobTime(String jobName, String time)
			throws SchedulerException, ParseException {
		Scheduler sched = sf.getScheduler();
		Trigger trigger = sched.getTrigger(jobName, TRIGGER_GROUP_NAME);
		if (trigger != null) {
			CronTrigger ct = (CronTrigger) trigger;
			ct.setCronExpression(time);
			sched.resumeTrigger(jobName, TRIGGER_GROUP_NAME);
		}
	}

	/**
	 * @Title: modifyJobTime
	 * @Description: 修改任务触发时间
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 * @return void
	 * @throws
	 */
	public static void modifyJobTime(String triggerName,
			String triggerGroupName, String time) throws SchedulerException,
			ParseException {
		Scheduler sched = sf.getScheduler();
		Trigger trigger = sched.getTrigger(triggerName, triggerGroupName);
		if (trigger != null) {
			CronTrigger ct = (CronTrigger) trigger;
			// 修改时间
			ct.setCronExpression(time);
			// 重启触发器
			sched.resumeTrigger(triggerName, triggerGroupName);
		}
	}

	/**
	 * @Title: removeJob
	 * @Description: 移除任务
	 * @param jobName
	 * @throws SchedulerException
	 * @return void
	 * @throws
	 */
	public static void removeJob(String jobName) throws SchedulerException {
		Scheduler sched = sf.getScheduler();
		sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器
		sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器
		sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务
	}

	/**
	 * @Title: removeJob
	 * @Description: 移除任务
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @throws SchedulerException
	 * @return void
	 * @throws
	 */
	public static void removeJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName)
			throws SchedulerException {
		Scheduler sched = sf.getScheduler();
		sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
		sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
		sched.deleteJob(jobName, jobGroupName);// 删除任务
	}
}
