package com.yub.system.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 编辑角色请求 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 编辑角色请求参数（角色编码不可修改）
 * @Version: 1.0.0
 */
@Data
public class RoleUpdateReqDTO {

    /** 角色ID */
    @NotNull(message = "角色ID不能为空")
    private Long id;

    /** 角色名称 */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称长度不能超过50个字符")
    private String name;

    /** 排序 */
    private Integer sort;

    /** 状态（1=启用 0=禁用） */
    @NotNull(message = "状态不能为空")
    private Integer status;

    /** 描述 */
    @Size(max = 200, message = "描述长度不能超过200个字符")
    private String description;

    /** 菜单权限ID列表 */
    @NotEmpty(message = "菜单权限不能为空")
    private List<Long> menuIds;
}
