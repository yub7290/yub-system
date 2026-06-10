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
    private Long id;
    private Long userId;
    private Long roleId;
}
