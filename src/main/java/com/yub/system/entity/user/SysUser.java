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
    private Long id;
    private String account;
    private String password;
    private String nickName;
    private String phone;
    private String email;
    private String avatarUrl;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createBy;
    private Long updateBy;
    private Integer deleted;
}
