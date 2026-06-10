package com.yub.system.service.auth.impl;

import com.yub.framework.security.SysUserDetailsLoader;
import com.yub.system.entity.user.SysUser;
import com.yub.system.mapper.user.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户详情加载器实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 从数据库加载用户详情
 * @Version: 1.0.0
 */
@Component
@RequiredArgsConstructor
public class SysUserDetailsLoaderImpl implements SysUserDetailsLoader {

    private final SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadByUserId(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null || user.getStatus() == 0) {
            throw new RuntimeException("用户不存在或已禁用");
        }
        List<String> roleCodes = sysUserMapper.selectRoleCodesByUserId(userId);
        List<SimpleGrantedAuthority> authorities = roleCodes.stream()
                .map(code -> new SimpleGrantedAuthority("ROLE_" + code.toUpperCase()))
                .collect(Collectors.toList());
        return User.builder()
                .username(String.valueOf(user.getId()))
                .password(user.getPassword())
                .authorities(authorities)
                .accountLocked(user.getStatus() == 0)
                .build();
    }
}
