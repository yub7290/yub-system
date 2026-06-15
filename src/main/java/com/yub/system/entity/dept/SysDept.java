package com.yub.system.entity.dept;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 部门实体
 * @Version: 1.0.0
 */
@Data
public class SysDept {
    /**
     * 部门ID
     */
    private Long id;
    /**
     * 父部门ID（0为根）
     */
    private Long parentId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门编码（唯一）
     */
    private String deptCode;
    /**
     * 排序（越小越靠前）
     */
    private Integer sort;
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
    /**
     * 子部门列表
     */
    private List<SysDept> children;
}
