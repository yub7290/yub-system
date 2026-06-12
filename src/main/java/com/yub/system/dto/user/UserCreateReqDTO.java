package com.yub.system.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 新增用户请求 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 新增用户请求参数
 * @Version: 1.0.0
 */
@Data
public class UserCreateReqDTO {

    /** 账号 */
    @NotBlank(message = "账号不能为空")
    @Size(min = 1, max = 50, message = "账号长度1-50个字符")
    private String account;

    /** 密码 */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 100, message = "密码长度6-100个字符")
    private String password;

    /** 昵称 */
    private String nickName;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 状态（1=启用 0=禁用，默认启用） */
    private Integer status = 1;

    /** 角色ID列表 */
    private List<Long> roleIds;

    /** 部门ID */
    private Long deptId;
}
