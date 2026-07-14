package com.yub.system.dto.menu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 新增菜单请求 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 新增菜单请求参数
 * @Version: 1.0.0
 */
@Data
public class MenuCreateReqDTO {

    /** 父菜单ID（0=顶级） */
    @NotNull(message = "父菜单不能为空")
    private Long parentId;

    /** 菜单名称 */
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    private String name;

    /** 路由路径 */
    @Size(max = 200, message = "路由路径长度不能超过200个字符")
    private String path;

    /** 组件路径 */
    @Size(max = 200, message = "组件路径长度不能超过200个字符")
    private String component;

    /** 图标 */
    @Size(max = 50, message = "图标长度不能超过50个字符")
    private String icon;

    /** 排序 */
    private Integer sort = 0;

    /** 菜单类型（0=目录 1=菜单 2=按钮） */
    @NotNull(message = "菜单类型不能为空")
    private Integer menuType;

    /** 权限标识 */
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_:]*$", message = "权限标识只能包含字母、数字、下划线和冒号")
    private String permission;

    /** 状态（1=正常 0=禁用，默认启用） */
    @NotNull(message = "状态不能为空")
    private Integer status = 1;
}
