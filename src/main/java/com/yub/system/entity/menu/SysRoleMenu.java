package com.yub.system.entity.menu;

import lombok.Data;

/**
 * 角色菜单关联实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 角色菜单关联
 * @Version: 1.0.0
 */
@Data
public class SysRoleMenu {
    private Long id;
    private Long roleId;
    private Long menuId;
}
