package com.yub.system.service.role.impl;

import com.yub.framework.redis.RedisUtils;
import com.yub.system.entity.role.SysRole;
import com.yub.system.mapper.role.SysRoleMapper;
import com.yub.system.service.role.SysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

/**
 * 角色 Service 实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-12
 * @Description: 角色业务实现
 * @Version: 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private static final String ROLE_OPTIONS_CACHE_KEY = "system:role:options";
    private static final Duration CACHE_TTL = Duration.ofMinutes(5);

    private final SysRoleMapper sysRoleMapper;

    @Override
    @SuppressWarnings("unchecked")
    public List<SysRole> selectAllEnabled() {
        return RedisUtils.get(ROLE_OPTIONS_CACHE_KEY)
                .map(cached -> (List<SysRole>) cached)
                .orElseGet(() -> {
                    List<SysRole> roles = sysRoleMapper.selectAllEnabled();
                    RedisUtils.set(ROLE_OPTIONS_CACHE_KEY, roles, CACHE_TTL);
                    return roles;
                });
    }
}
