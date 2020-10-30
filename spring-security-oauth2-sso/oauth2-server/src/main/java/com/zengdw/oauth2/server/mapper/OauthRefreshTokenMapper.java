package com.zengdw.oauth2.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zengdw.oauth2.server.entity.OauthRefreshToken;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: zengd
 * @date: 2020/10/30 14:57
 */
@Mapper
public interface OauthRefreshTokenMapper extends BaseMapper<OauthRefreshToken> {
}