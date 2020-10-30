package com.zengdw.oauth2.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zengdw.oauth2.server.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: zengd
 * @date: 2020/10/30 15:05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}