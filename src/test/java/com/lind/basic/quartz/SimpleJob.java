package com.lind.basic.quartz;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 简单任务调度.
 */
@Slf4j
public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info(String.format("任务调度SimpleJob 时间:" + DateUtil.now()));
    }
}
