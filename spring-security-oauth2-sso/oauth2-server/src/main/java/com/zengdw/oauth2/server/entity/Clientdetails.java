package com.zengdw.oauth2.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @description:
 * @author: zengd
 * @date: 2020/10/30 14:57
 */
@Data
@TableName(value = "clientdetails")
public class Clientdetails implements Serializable {
    @TableId(value = "appId", type = IdType.INPUT)
    private String appid;

    @TableField(value = "resourceIds")
    private String resourceids;

    @TableField(value = "appSecret")
    private String appsecret;

    @TableField(value = "scope")
    private String scope;

    @TableField(value = "grantTypes")
    private String granttypes;

    @TableField(value = "redirectUrl")
    private String redirecturl;

    @TableField(value = "authorities")
    private String authorities;

    @TableField(value = "access_token_validity")
    private Integer accessTokenValidity;

    @TableField(value = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @TableField(value = "additionalInformation")
    private String additionalinformation;

    @TableField(value = "autoApproveScopes")
    private String autoapprovescopes;

    private static final long serialVersionUID = 1L;
}