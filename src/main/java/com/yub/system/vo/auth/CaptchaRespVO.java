package com.yub.system.vo.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 验证码响应VO
 * @Version: 1.0
 */
@Getter
@Setter
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
