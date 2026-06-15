package com.yub.system.vo.dept;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 部门详情响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 部门详情响应（含父部门名称）
 * @Version: 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptDetailRespVO {

    /** 部门ID */
    private Long id;

    /** 部门名称 */
    private String deptName;

    /** 部门编码 */
    private String deptCode;

    /** 父部门ID */
    private Long parentId;

    /** 父部门名称 */
    private String parentName;

    /** 排序 */
    private Integer sort;

    /** 状态（1=启用 0=禁用） */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 创建人 */
    private Long createBy;

    /** 更新人 */
    private Long updateBy;
}
