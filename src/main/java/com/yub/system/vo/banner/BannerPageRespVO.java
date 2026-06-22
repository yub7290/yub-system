package com.yub.system.vo.banner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Banner分页响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-22
 * @Description: Banner分页列表响应
 * @Version: 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerPageRespVO {

    /** Banner ID */
    private Long id;

    /** 图片URL */
    private String imageUrl;

    /** 链接URL */
    private String linkUrl;

    /** 排序 */
    private Integer sort;

    /** 状态（1=启用 0=禁用） */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;
}
