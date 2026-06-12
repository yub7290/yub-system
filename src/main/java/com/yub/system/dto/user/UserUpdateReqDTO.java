package com.yub.system.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 编辑用户请求 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 编辑用户请求参数
 * @Version: 1.0.0
 */
@Data
public class UserUpdateReqDTO {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long id;

    /** 昵称 */
    @NotBlank(message = "昵称不能为空")
    @Size(max = 50, message = "昵称长度不能超过50个字符")
    private String nickName;

    /** 手机号 */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /** 邮箱 */
    private String email;

    /** 状态（1=启用 0=禁用） */
    private Integer status;

    /** 角色ID列表 */
    @NotEmpty(message = "角色不能为空")
    private List<Long> roleIds;

    /** 部门ID */
    private Long deptId;
}
