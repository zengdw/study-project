package com.zengdw.quartz.jobs;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zengd
 * @version 1.0
 * @date Created in 2021/4/27 13:59
 * @description 同步用户job
 */
public class SyncUserJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        //获取JobDetail中传递的参数
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String userName = (String) jobDataMap.get("userName");
        String blogUrl = (String) jobDataMap.get("blogUrl");
        String blogRemark = (String) jobDataMap.get("blogRemark");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("用户名称：" + userName);
        System.out.println("博客地址：" + blogUrl);
        System.out.println("博客信息：" + blogRemark);
        System.out.println("创建时间：" + now.format(pattern));
        System.out.println("--------------------------------");
    }
}
