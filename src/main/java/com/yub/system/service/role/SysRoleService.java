package com.yub.system.service.role;

import com.yub.system.entity.role.SysRole;

import java.util.List;

/**
 * 角色 Service 接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-12
 * @Description: 角色业务接口
 * @Version: 1.0.0
 */
public interface SysRoleService {

    /**
     * 获取所有启用角色
     *
     * @return 角色列表
     */
    List<SysRole> selectAllEnabled();
}
