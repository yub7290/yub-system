package com.yub.system.dto.log;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志分页查询参数
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 操作日志分页查询请求参数
 * @Version: 1.0.0
 */
@Data
public class OperaLogQueryDTO {

    /** 用户ID */
    private Long userId;

    /** 模块 */
    private String module;

    /** 操作类型 */
    private String operaType;

    /** 开始时间 */
    private LocalDateTime startTime;

    /** 结束时间 */
    private LocalDateTime endTime;
}
