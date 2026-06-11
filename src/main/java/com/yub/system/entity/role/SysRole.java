package com.yub.system.entity.role;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 角色实体
 * @Version: 1.0.0
 */
@Data
public class SysRole {
    /**
     * 角色ID
     */
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色编码
     */
    private String code;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 状态（1=正常 0=禁用）
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 逻辑删除（0=正常 1=已删除）
     */
    private Integer deleted;
}
