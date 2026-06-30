package com.yub.system.vo.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 操作日志分页响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 操作日志分页列表响应
 * @Version: 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperaLogPageRespVO {

    /** 主键 */
    private Long id;

    /** 操作人ID */
    private Long userId;

    /** 操作人用户名 */
    private String nickName;

    /** 模块 */
    private String module;

    /** 操作类型 */
    private String operaType;

    /** 操作内容摘要 */
    private String content;

    /** 方法名 */
    private String methodName;

    /** IP地址 */
    private String ip;

    /** 耗时(ms) */
    private Integer costTime;

    /** 创建时间 */
    private LocalDateTime createTime;
}
