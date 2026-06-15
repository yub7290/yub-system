package com.yub.system.dto.dept;

import lombok.Data;

/**
 * 部门分页查询参数
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 部门树查询请求参数
 * @Version: 1.0.0
 */
@Data
public class DeptQueryDTO {

    /** 部门名称（模糊搜索） */
    private String deptName;

    /** 状态（1=启用 0=禁用） */
    private Integer status;
}
