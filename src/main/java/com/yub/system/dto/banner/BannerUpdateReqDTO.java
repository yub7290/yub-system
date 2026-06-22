package com.yub.system.dto.banner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 编辑Banner请求 DTO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-22
 * @Description: 编辑Banner请求参数
 * @Version: 1.0.0
 */
@Data
public class BannerUpdateReqDTO {

    /** Banner ID */
    @NotNull(message = "Banner ID不能为空")
    private Long id;

    /** 图片URL */
    @NotBlank(message = "图片URL不能为空")
    @Size(max = 500, message = "图片URL长度不能超过500个字符")
    private String imageUrl;

    /** 链接URL */
    @Size(max = 500, message = "链接URL长度不能超过500个字符")
    private String linkUrl;

    /** 排序 */
    private Integer sort;

    /** 状态（1=启用 0=禁用） */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
