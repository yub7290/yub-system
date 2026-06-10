package com.yub.system.dto.auth;

import com.yub.system.entity.menu.SysMenu;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private UserInfo userInfo;
    private List<SysMenu> menus;

    @Data
    @Builder
    public static class UserInfo {
        private Long id;
        private String account;
        private String nickName;
        private String avatarUrl;
    }
}
