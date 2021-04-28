package com.zengdw.quartz.config;

import com.zengdw.quartz.jobs.SyncUserJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengd
 * @version 1.0
 * @date Created in 2021/4/27 14:06
 * @description
 */
@Configuration
public class QuartzConfig {
    private static final String JOB_GROUP_NAME = "PJB_JOBGROUP_NAME";
    private static final String TRIGGER_GROUP_NAME = "PJB_TRIGGERGROUP_NAME";

    /**
     * 定时任务1：
     * 同步用户信息Job（任务详情）
     */
    @Bean
    public JobDetail syncUserJobDetail() {
        return JobBuilder.newJob(SyncUserJob.class)
                .withIdentity("syncUserJob", JOB_GROUP_NAME)
                //设置参数（键值对）
                .usingJobData("userName", "pan_junbiao的博客")
                .usingJobData("blogUrl", "https://blog.csdn.net/pan_junbiao")
                .usingJobData("blogRemark", "您好，欢迎访问 pan_junbiao的博客")
                .storeDurably() //即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }

    /**
     * 定时任务1：
     * 同步用户信息Job（触发器）
     */
    @Bean
    public Trigger syncUserJobTrigger() {
        //每隔5秒执行一次
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");

        //创建触发器
        return TriggerBuilder.newTrigger()
                //关联上述的JobDetail
                .forJob(syncUserJobDetail())
                //给Trigger起个名字
                .withIdentity("syncUserJobTrigger", TRIGGER_GROUP_NAME)
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
