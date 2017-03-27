package com.hjc.demo.task;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hjc.test.Demo;

/**
 * @ClassName: TestJob
 * @Description: 测试Job
 * @author 何金成
 * @date 2016年4月28日 下午10:16:04
 * 
 */
public class TestJob implements Job {

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		Demo.print("现在时间：" + new Date().toLocaleString());
	}

}
