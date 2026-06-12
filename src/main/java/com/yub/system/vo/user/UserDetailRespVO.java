package com.yub.system.vo.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户详情响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 用户详情响应
 * @Version: 1.0.0
 */
@Data
@Builder
public class UserDetailRespVO {

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

    /** 头像URL */
    private String avatarUrl;

    /** 状态（1=启用 0=禁用） */
    private Integer status;

    /** 部门ID */
    private Long deptId;

    /** 角色ID列表 */
    private List<Long> roleIds;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 创建人 */
    private Long createBy;

    /** 更新人 */
    private Long updateBy;

    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;

    /** 最后登录IP */
    private String lastLoginIp;

    /** 登录次数 */
    private Integer loginCount;
}
