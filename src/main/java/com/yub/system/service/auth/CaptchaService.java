package com.yub.system.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * 验证码服务接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 验证码服务
 * @Version: 1.0.0
 */
public interface CaptchaService {

    Map<String, String> generate(HttpServletRequest request, HttpServletResponse response);
}
