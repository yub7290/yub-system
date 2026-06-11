package com.yub.system.entity.user;

import lombok.Data;

/**
 * 用户角色关联实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 用户角色关联
 * @Version: 1.0.0
 */
@Data
public class SysUserRole {
    /**
     * 关联ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;
}
