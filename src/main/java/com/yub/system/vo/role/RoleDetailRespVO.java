package com.yub.system.vo.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色详情响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 角色详情响应（含菜单权限ID列表）
 * @Version: 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDetailRespVO {

    /** 角色ID */
    private Long id;

    /** 角色名称 */
    private String name;

    /** 角色编码 */
    private String code;

    /** 排序 */
    private Integer sort;

    /** 状态（1=启用 0=禁用） */
    private Integer status;

    /** 描述 */
    private String description;

    /** 菜单权限ID列表 */
    private List<Long> menuIds;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 创建人 */
    private Long createBy;

    /** 更新人 */
    private Long updateBy;
}
