package com.yub.system.entity.log;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 登录日志实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 登录日志
 * @Version: 1.0.0
 */
@Data
public class SysAccessLog {
    private Long id;
    private Long userId;
    private String nickName;
    private String ip;
    private String userAgent;
    private Integer status;
    private String errorMsg;
    private LocalDateTime createTime;
}
