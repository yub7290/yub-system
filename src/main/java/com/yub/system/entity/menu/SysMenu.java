package com.yub.system.entity.menu;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 菜单实体
 * @Version: 1.0.0
 */
@Data
public class SysMenu {
    /**
     * 菜单ID
     */
    private Long id;
    /**
     * 父菜单ID（0=顶级）
     */
    private Long parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 路由路径
     */
    private String path;
    /**
     * 前端组件路径
     */
    private String component;
    /**
     * 菜单元数据
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 菜单类型（0=目录 1=菜单 2=按钮）
     */
    private Integer menuType;
    /**
     * 权限标识
     */
    private String permission;
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
     * 逻辑删除（0=正常 1=已删除）
     */
    private Integer deleted;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 子菜单列表
     */
    private List<SysMenu> children;
}
