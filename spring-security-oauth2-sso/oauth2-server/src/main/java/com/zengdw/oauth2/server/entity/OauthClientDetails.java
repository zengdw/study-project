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
@TableName(value = "oauth_client_details")
public class OauthClientDetails implements Serializable {
    @TableId(value = "client_id", type = IdType.INPUT)
    private String clientId;

    @TableField(value = "resource_ids")
    private String resourceIds;

    @TableField(value = "client_secret")
    private String clientSecret;

    @TableField(value = "scope")
    private String scope;

    @TableField(value = "authorized_grant_types")
    private String authorizedGrantTypes;

    @TableField(value = "web_server_redirect_uri")
    private String webServerRedirectUri;

    @TableField(value = "authorities")
    private String authorities;

    @TableField(value = "access_token_validity")
    private Integer accessTokenValidity;

    @TableField(value = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @TableField(value = "additional_information")
    private String additionalInformation;

    @TableField(value = "autoapprove")
    private String autoapprove;

    private static final long serialVersionUID = 1L;
}