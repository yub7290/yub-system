package com.yub.system.entity.param;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统参数实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-07-03
 * @Description: 系统参数实体
 * @Version: 1.0
 */
@Data
public class SysParam {
    /**
     * 主键
     */
    private Long id;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数编码
     */
    private String code;
    /**
     * 参数值
     */
    private String value;
    /**
     * 参数分组编码
     */
    private String groupCode;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序
     */
    private Integer sort;
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
}
