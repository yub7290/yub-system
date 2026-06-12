package com.yub.system.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户分页响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 用户分页列表响应
 * @Version: 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPageRespVO {

    /** 用户ID */
    private Long id;

    /** 账号 */
    private String account;

    /** 昵称 */
    private String nickName;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 状态（1=启用 0=禁用） */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;
}
