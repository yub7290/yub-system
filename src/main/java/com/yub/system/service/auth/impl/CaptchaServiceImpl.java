package com.yub.system.service.auth.impl;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import com.yub.system.service.auth.CaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

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

    private final RedissonClient redissonClient;

    private static final String CAPTCHA_PREFIX = "captcha:";
    private static final int CAPTCHA_EXPIRE_MINUTES = 5;
    private static final int CAPTCHA_WIDTH = 130;
    private static final int CAPTCHA_HEIGHT = 48;
    private static final int CAPTCHA_LENGTH = 4;

    @Override
    public Map<String, String> generate(HttpServletRequest request, HttpServletResponse response) {
        SpecCaptcha captcha = new SpecCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, CAPTCHA_LENGTH);
        String text = captcha.text();
        String base64 = captcha.toBase64();

        RBucket<String> bucket = redissonClient.getBucket(CAPTCHA_PREFIX + text);
        bucket.set(text, Duration.ofMinutes(CAPTCHA_EXPIRE_MINUTES));

        Map<String, String> result = new HashMap<>();
        result.put("key", text);
        result.put("image", base64);
        return result;
    }
}
