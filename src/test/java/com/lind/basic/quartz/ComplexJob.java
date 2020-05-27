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
public class ComplexJob implements Job {
    /**
     * 若参数变量名修改 QuartzJobController中也需对应修改
     */
    private String parameter;

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("任务调度ComplexJob 时间:{} param:{}", DateUtil.now(), parameter);
    }
}
