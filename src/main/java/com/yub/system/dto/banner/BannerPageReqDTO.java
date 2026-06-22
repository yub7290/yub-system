package com.yub.system.dto.banner;

import lombok.Data;

/**
 * Banner分页查询参数
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-22
 * @Description: Banner分页查询请求参数
 * @Version: 1.0.0
 */
@Data
public class BannerPageReqDTO {

    /** 状态（1=启用 0=禁用） */
    private Integer status;
}
