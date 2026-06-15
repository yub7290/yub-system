package com.yub.system.vo.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 角色分页响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 角色分页列表响应
 * @Version: 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePageRespVO {

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

    /** 创建时间 */
    private LocalDateTime createTime;
}
