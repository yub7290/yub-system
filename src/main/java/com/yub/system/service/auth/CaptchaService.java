package com.yub.system.service.auth;

import com.yub.system.vo.auth.CaptchaRespVO;

/**
 * 验证码服务接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 验证码服务
 * @Version: 1.0.0
 */
public interface CaptchaService {

    /**
     * 生成图形验证码
     *
     * @return key=验证码标识, image=Base64图片
     */
    CaptchaRespVO generate();
}
