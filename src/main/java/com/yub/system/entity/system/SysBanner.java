package com.yub.system.entity.system;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Banner实体
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-22
 * @Description: Banner管理实体
 * @Version: 1.0.0
 */
@Data
public class SysBanner {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 图片URL
     */
    private String imageUrl;

    /**
     * 链接URL
     */
    private String linkUrl;

    /**
     * 排序（越小越靠前）
     */
    private Integer sort;

    /**
     * 状态（1启用 0禁用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateUser;
}
