package com.zengdw.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengdw.quartz.domian.QrtzCronTriggers;
import com.zengdw.quartz.domian.QrtzJobDetails;
import com.zengdw.quartz.mapper.QrtzCronTriggersMapper;
import com.zengdw.quartz.mapper.QrtzJobDetailsMapper;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zengd
 * @version 1.0
 * @date Created in 2021/4/27 14:57
 * @description
 */
@RestController
public class QuartzController {
    @Resource
    private QrtzJobDetailsMapper jobDetailsMapper;
    @Resource
    private QrtzCronTriggersMapper cronTriggersMapper;
//    @Resource
//    private SchedulerFactoryBean schedulerFactoryBean;
    @Resource
    private Scheduler scheduler;

    /*@PostConstruct
    public void init(){
        scheduler = schedulerFactoryBean.getScheduler();
    }*/


    @GetMapping("/detailQuery")
    public List<QrtzJobDetails> detailQuery() {
        QueryWrapper<QrtzJobDetails> wrapper = new QueryWrapper<>();
        return jobDetailsMapper.selectList(wrapper);
    }

    @GetMapping("/cronQuery")
    public List<QrtzCronTriggers> cronQuery() {
        QueryWrapper<QrtzCronTriggers> wrapper = new QueryWrapper<>();
        return cronTriggersMapper.selectList(wrapper);
    }

    @GetMapping("/pauseAll")
    public void pauseAll() {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/pauseTrigger/{name}/{group}")
    public void pauseTrigger(@PathVariable String name, @PathVariable String group){
        try {
            scheduler.pauseTrigger(new TriggerKey(name, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/resumeTrigger/{name}/{group}")
    public void resumeTrigger(@PathVariable String name, @PathVariable String group) {
        try {
            scheduler.resumeTrigger(new TriggerKey(name, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/triggerJob/{name}/{group}")
    public void triggerJob(@PathVariable String name, @PathVariable String group){
        try {
            scheduler.triggerJob(new JobKey(name, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/resumeJob/{name}/{group}")
    public void resumeJob(@PathVariable String name, @PathVariable String group){
        try {
            scheduler.resumeJob(new JobKey(name, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
