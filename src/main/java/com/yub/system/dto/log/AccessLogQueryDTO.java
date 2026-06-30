package com.yub.system.dto.log;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 访问日志分页查询参数
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 访问日志分页查询请求参数
 * @Version: 1.0.0
 */
@Data
public class AccessLogQueryDTO {

    /** 用户ID */
    private Long userId;

    /** URL（模糊搜索） */
    private String url;

    /** HTTP 方法 */
    private String method;

    /** 状态（1=成功 0=失败） */
    private Integer status;

    /** 事件类型 */
    private String eventType;

    /** 开始时间 */
    private LocalDateTime startTime;

    /** 结束时间 */
    private LocalDateTime endTime;
}
