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
    private Long id;
    private Long parentId;
    private String name;
    private String path;
    private String component;
    private String icon;
    private Integer sort;
    private Integer menuType;
    private String permission;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
    private List<SysMenu> children;
}
