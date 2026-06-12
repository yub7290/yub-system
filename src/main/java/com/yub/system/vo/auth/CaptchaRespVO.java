package com.yub.system.vo.auth;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 验证码响应VO
 * @Version: 1.0
 */
@Data
@Builder
public class CaptchaRespVO {
    /**
     * 验证码的key
     */
    private String key;

    /**
     * 验证码的图片
     */
    private String image;

}
