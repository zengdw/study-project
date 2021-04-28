package com.zengdw.quartz.domian;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "qrtz_cron_triggers")
public class QrtzCronTriggers implements Serializable {
    @TableField(value = "SCHED_NAME")
    private String schedName;

    @TableField(value = "TRIGGER_NAME")
    private String triggerName;

    @TableField(value = "TRIGGER_GROUP")
    private String triggerGroup;

    @TableField(value = "CRON_EXPRESSION")
    private String cronExpression;

    @TableField(value = "TIME_ZONE_ID")
    private String timeZoneId;

    private static final long serialVersionUID = 1L;
}