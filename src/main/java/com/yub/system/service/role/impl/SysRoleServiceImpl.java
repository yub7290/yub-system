package com.yub.system.service.role.impl;

import com.yub.system.entity.role.SysRole;
import com.yub.system.mapper.role.SysRoleMapper;
import com.yub.system.service.role.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色 Service 实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-12
 * @Description: 角色业务实现
 * @Version: 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> selectAllEnabled() {
        return sysRoleMapper.selectAllEnabled();
    }
}
