package com.zengdw.quartz.domian;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zengd
 */
@Data
@TableName(value = "qrtz_job_details")
public class QrtzJobDetails implements Serializable {
    @TableField(value = "SCHED_NAME")
    private String schedName;

    @TableField(value = "JOB_NAME")
    private String jobName;

    @TableField(value = "JOB_GROUP")
    private String jobGroup;

    @TableField(value = "DESCRIPTION")
    private String description;

    @TableField(value = "JOB_CLASS_NAME")
    private String jobClassName;

    @TableField(value = "IS_DURABLE")
    private String isDurable;

    @TableField(value = "IS_NONCONCURRENT")
    private String isNonconcurrent;

    @TableField(value = "IS_UPDATE_DATA")
    private String isUpdateData;

    @TableField(value = "REQUESTS_RECOVERY")
    private String requestsRecovery;

    @TableField(value = "JOB_DATA")
    private byte[] jobData;

    private static final long serialVersionUID = 1L;
}