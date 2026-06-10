package com.yub.system.exception;

import com.yub.common.exception.BaseException;

/**
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 系统管理模块异常
 * @Version: 1.0
 */
public class SystemException extends BaseException {
    public SystemException(SystemErrorCode errorCode) {
        super(errorCode);
    }

    public SystemException(SystemErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
