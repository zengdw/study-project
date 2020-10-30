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
@TableName(value = "oauth_client_token")
public class OauthClientToken implements Serializable {
    @TableId(value = "authentication_id", type = IdType.INPUT)
    private String authenticationId;

    @TableField(value = "token_id")
    private String tokenId;

    @TableField(value = "token")
    private String token;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "client_id")
    private String clientId;

    private static final long serialVersionUID = 1L;
}