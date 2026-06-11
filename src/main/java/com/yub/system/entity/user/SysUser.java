package com.yub.system.entity.user;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 系统用户实体
 * @Version: 1.0.0
 */
@Data
public class SysUser {
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码（BCrypt加密）
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像URL
     */
    private String avatarUrl;
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
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    /**
     * 登录次数
     */
    private Integer loginCount;
}
