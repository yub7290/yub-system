package com.yub.system.exception;

import com.yub.common.exception.ErrorCode;
import lombok.AllArgsConstructor;

/**
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 系统管理模块异常枚举
 * @Version: 1.0
 */
@AllArgsConstructor
public enum SystemErrorCode implements ErrorCode {

    CAPTCHA_ERROR(100001, "验证码错误"),
    PASSWORD_ERROR(100002, "密码错误"),
    ACCOUNT_DISABLED(100003, "账号已禁用"),
    TOKEN_EXPIRED(100004, "令牌已过期"),
    TOKEN_INVALID(100005, "令牌无效");

    private final int code;
    private final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
