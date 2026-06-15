package com.yub.system.vo.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 菜单详情响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 菜单详情响应
 * @Version: 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDetailRespVO {

    /** 菜单ID */
    private Long id;

    /** 父菜单ID */
    private Long parentId;

    /** 菜单名称 */
    private String name;

    /** 路由路径 */
    private String path;

    /** 组件路径 */
    private String component;

    /** 图标 */
    private String icon;

    /** 排序 */
    private Integer sort;

    /** 菜单类型（0=目录 1=菜单 2=按钮） */
    private Integer menuType;

    /** 权限标识 */
    private String permission;

    /** 状态（1=正常 0=禁用） */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
