package com.yub.system.entity.log;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统操作日志实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 系统操作日志实体，对应 sys_opera_log 表
 * @Version: 1.0.0
 */
@Data
public class SysOperaLog {

    /** 主键 */
    private Long id;

    /** 操作人ID */
    private Long userId;

    /** 操作人用户名 */
    private String nickName;

    /** 模块（如 dict/role/course） */
    private String module;

    /** 操作类型（CREATE/UPDATE/DELETE/QUERY/IMPORT/EXPORT） */
    private String operaType;

    /** 操作内容摘要 */
    private String content;

    /** 方法全名（包名+类名+方法名） */
    private String methodName;

    /** 请求参数（JSON，脱敏） */
    private String requestParams;

    /** 响应结果（JSON，截断） */
    private String responseResult;

    /** IP地址 */
    private String ip;

    /** 耗时(ms) */
    private Integer costTime;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 创建人 */
    private Long createBy;

    /** 更新人 */
    private Long updateBy;
}
