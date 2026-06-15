package com.yub.system.dto.role;

import lombok.Data;

/**
 * 角色分页查询参数
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 角色分页查询请求参数
 * @Version: 1.0.0
 */
@Data
public class RoleQueryDTO {

    /** 角色名称（模糊搜索） */
    private String name;

    /** 状态（1=启用 0=禁用） */
    private Integer status;
}
