package com.yub.system.service.auth.impl;

import com.wf.captcha.SpecCaptcha;
import com.yub.common.constant.RedisKeyConstants;
import com.yub.common.util.IdUtils;
import com.yub.framework.redis.RedisUtils;
import com.yub.system.service.auth.CaptchaService;
import com.yub.system.vo.auth.CaptchaRespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 验证码服务实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 验证码服务实现
 * @Version: 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    private static final int CAPTCHA_EXPIRE_MINUTES = 5;
    private static final int CAPTCHA_WIDTH = 130;
    private static final int CAPTCHA_HEIGHT = 48;
    private static final int CAPTCHA_LENGTH = 4;

    /**
     * 生成算术验证码图片，将验证码文本存入Redis并返回Base64图片和key
     */
    @Override
    public CaptchaRespVO generate() {
        SpecCaptcha captcha = new SpecCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, CAPTCHA_LENGTH);
        String text = captcha.text();
        String base64 = captcha.toBase64();

        String captchaKey = IdUtils.simpleUuid();
        RedisUtils.set(RedisKeyConstants.CAPTCHA_PREFIX + captchaKey, text, CAPTCHA_EXPIRE_MINUTES);

        return CaptchaRespVO.builder()
                .key(captchaKey)
                .image(base64)
                .build();
    }
}
