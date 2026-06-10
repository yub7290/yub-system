package com.yub.system.entity.role;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 角色实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 角色实体
 * @Version: 1.0.0
 */
@Data
public class SysRole {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createBy;
    private Long updateBy;
    private Integer deleted;
}
