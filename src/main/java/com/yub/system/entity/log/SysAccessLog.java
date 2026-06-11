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
    /**
     * 日志ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 客户端IP
     */
    private String ip;
    /**
     * 浏览器UserAgent
     */
    private String userAgent;
    /**
     * 登录状态（1=成功 0=失败）
     */
    private Integer status;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * HTTP方法
     */
    private String method;
    /**
     * 请求URL
     */
    private String url;
    /**
     * 事件类型
     */
    private String eventType;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
