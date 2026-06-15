package com.yub.system.dto.dept;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 新增部门请求 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 新增部门请求参数
 * @Version: 1.0.0
 */
@Data
public class DeptCreateReqDTO {

    /** 部门名称 */
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 50, message = "部门名称长度不能超过50个字符")
    private String deptName;

    /** 部门编码 */
    @Size(max = 50, message = "部门编码长度不能超过50个字符")
    private String deptCode;

    /** 父部门ID（0为根） */
    @NotNull(message = "父部门不能为空")
    private Long parentId;

    /** 排序 */
    private Integer sort = 0;

    /** 状态（1=启用 0=禁用，默认启用） */
    @NotNull(message = "状态不能为空")
    private Integer status = 1;
}
