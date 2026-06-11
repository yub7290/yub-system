package com.yub.system.exception;

import com.yub.common.exception.BaseException;

/**
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 系统管理模块异常
 * @Version: 1.0
 */
public class SystemException extends BaseException {
    /**
     * 使用错误码构造异常
     */
    public SystemException(SystemErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码和原始异常构造
     *
     * @param errorCode 系统错误码
     * @param cause     原始异常
     */
    public SystemException(SystemErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
