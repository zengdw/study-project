package com.zengdw.oauth2.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @description:
 * @author: zengd
 * @date: 2020/10/30 14:57
 */
@Data
@TableName(value = "oauth_approvals")
public class OauthApprovals implements Serializable {
    @TableField(value = "userId")
    private String userid;

    @TableField(value = "clientId")
    private String clientid;

    @TableField(value = "scope")
    private String scope;

    @TableField(value = "status")
    private String status;

    @TableField(value = "expiresAt")
    private LocalDateTime expiresat;

    @TableField(value = "lastModifiedAt")
    private LocalDateTime lastmodifiedat;

    private static final long serialVersionUID = 1L;
}