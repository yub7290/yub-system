package com.yub.system.dto.user;

import jakarta.validation.constraints.NotNull;
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
    private String nickName;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 状态（1=启用 0=禁用） */
    private Integer status;

    /** 角色ID列表 */
    private List<Long> roleIds;

    /** 部门ID */
    private Long deptId;
}
