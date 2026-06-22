package com.yub.system.vo.banner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Banner详情响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-22
 * @Description: Banner详情响应
 * @Version: 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerDetailRespVO {

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

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 创建人 */
    private String createUser;

    /** 更新人 */
    private String updateUser;
}
