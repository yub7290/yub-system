package com.yub.system.dto.user;

import lombok.Data;

/**
 * 用户查询条件
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 用户分页查询与导出查询的查询条件DTO
 * @Version: 1.0.0
 */
@Data
public class UserQueryDTO {

    /**
     * 账号（模糊查询）
     */
    private String account;

    /**
     * 昵称（模糊查询）
     */
    private String nickName;

    /**
     * 手机号（模糊查询）
     */
    private String phone;

    /**
     * 状态（1启用 0禁用）
     */
    private Integer status;
}
