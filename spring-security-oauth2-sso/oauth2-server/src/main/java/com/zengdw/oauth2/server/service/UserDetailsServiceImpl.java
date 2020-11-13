package com.zengdw.oauth2.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengdw.oauth2.server.entity.User;
import com.zengdw.oauth2.server.mapper.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 用户管理
 * @author: zengd
 * @date: 2020/10/30 14:58
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("name", s);
        User user = userMapper.selectOne(wrapper);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("/index", "/test");
        return new org.springframework.security.core.userdetails.User(s, user.getPassword(), authorityList);
    }
}
