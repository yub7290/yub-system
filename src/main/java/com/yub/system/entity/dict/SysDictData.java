package com.yub.system.entity.dict;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典数据实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 字典数据实体
 * @Version: 1.0.0
 */
@Data
public class SysDictData {
    /**
     * 主键
     */
    private Long id;
    /**
     * 字典类型ID
     */
    private Long typeId;
    /**
     * 字典标签
     */
    private String label;
    /**
     * 字典值
     */
    private String value;
    /**
     * CSS类名
     */
    private String cssClass;
    /**
     * 列表样式
     */
    private String listClass;
    /**
     * 是否为默认值（1是 0否）
     */
    private Integer isDefault;
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
