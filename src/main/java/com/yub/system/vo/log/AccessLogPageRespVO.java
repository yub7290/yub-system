package com.yub.system.vo.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 访问日志分页响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 访问日志分页列表响应
 * @Version: 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogPageRespVO {

    /** 主键 */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 用户昵称 */
    private String nickName;

    /** 客户端IP */
    private String ip;

    /** HTTP 方法 */
    private String method;

    /** 请求 URL */
    private String url;

    /** 事件类型 */
    private String eventType;

    /** 状态（1=成功 0=失败） */
    private Integer status;

    /** 错误信息 */
    private String errorMsg;

    /** 耗时(ms) */
    private Integer costTime;

    /** 创建时间 */
    private LocalDateTime createTime;
}
