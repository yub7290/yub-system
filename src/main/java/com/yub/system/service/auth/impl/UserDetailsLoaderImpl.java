package com.yub.system.service.auth.impl;

import com.yub.common.enums.StatusEnum;
import com.yub.framework.security.UserDetailsLoader;
import com.yub.system.entity.user.SysUser;
import com.yub.system.mapper.user.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Duration;
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
public class UserDetailsLoaderImpl implements UserDetailsLoader {

    private static final String ROLE_CACHE_PREFIX = "role_cache:";
    private static final Duration ROLE_CACHE_TTL = Duration.ofMinutes(5);

    private final SysUserMapper sysUserMapper;
    private final RedissonClient redissonClient;

    /**
     * 从数据库加载用户详情，角色列表使用Redis缓存减少DB查询
     */
    @Override
    public UserDetails loadByUserId(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null || StatusEnum.isDisabled(user.getStatus())) {
            throw new UsernameNotFoundException("用户不存在或已禁用");
        }
        List<String> roleCodes = getRoleCodes(userId);
        List<SimpleGrantedAuthority> authorities = roleCodes.stream()
                .map(code -> new SimpleGrantedAuthority("ROLE_" + code.toUpperCase()))
                .collect(Collectors.toList());
        return User.builder()
                .username(String.valueOf(user.getId()))
                .password(user.getPassword())
                .authorities(authorities)
                .accountLocked(StatusEnum.isDisabled(user.getStatus()))
                .build();
    }

    @SuppressWarnings("unchecked")
    private List<String> getRoleCodes(Long userId) {
        RBucket<List<String>> bucket = redissonClient.getBucket(ROLE_CACHE_PREFIX + userId);
        List<String> cached = bucket.get();
        if (cached != null) {
            return cached;
        }
        List<String> roleCodes = sysUserMapper.selectRoleCodesByUserId(userId);
        bucket.set(roleCodes, ROLE_CACHE_TTL);
        return roleCodes;
    }
}
