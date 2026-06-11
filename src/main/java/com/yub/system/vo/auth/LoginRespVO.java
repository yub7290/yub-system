package com.yub.system.vo.auth;

import lombok.Builder;
import lombok.Data;

/**
 * 登录响应
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 登录响应数据
 * @Version: 1.0.0
 */
@Data
@Builder
public class LoginRespVO {

    /**
     * AccessToken
     */
    private String accessToken;
    /**
     * RefreshToken
     */
    private String refreshToken;
}
