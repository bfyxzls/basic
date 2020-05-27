package com.lind.basic.quartz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest()
public class JobTest {
    @Autowired
    private Scheduler scheduler;

    public static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }

    @Test
    public void simpleJobRun() throws InterruptedException {
        add("com.lind.basic.quartz.SimpleJob", "0/1 * * * * ?", null);
        Thread.sleep(5000);
        delete("com.lind.basic.quartz.SimpleJob");
        Thread.sleep(5000 * 5);
    }

    @Test
    public void complexJobRun() throws InterruptedException {
        add("com.lind.basic.quartz.ComplexJob", "0/1 * * * * ?", "lind");
        Thread.sleep(5000);
    }

    /**
     * 添加定时任务
     */
    public void add(String jobClassName, String cronExpression, String parameter) throws InterruptedException {
        try {
            // 启动调度器
            scheduler.start();

            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
                    .withIdentity(jobClassName)
                    .usingJobData("parameter", parameter)
                    .build();

            //表达式调度构建器(即任务执行的时间) 使用withMisfireHandlingInstructionDoNothing() 忽略掉调度暂停过程中没有执行的调度
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName)
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logger.error(e.toString());
            throw new IllegalArgumentException("创建定时任务失败");
        } catch (Exception e) {
            throw new IllegalArgumentException("后台找不到该类名任务");
        }
    }

    /**
     * 删除定时任务
     *
     * @param jobClassName
     */
    public void delete(String jobClassName) {

        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName));
        } catch (Exception e) {
            throw new IllegalArgumentException("删除定时任务失败");
        }
    }
}
