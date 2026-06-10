package com.yub.system.service.auth;

import com.yub.system.dto.auth.LoginRequest;
import com.yub.system.dto.auth.LoginResponse;
import com.yub.system.entity.menu.SysMenu;

import java.util.List;

/**
 * 认证服务接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 认证服务接口
 * @Version: 1.0.0
 */
public interface AuthService {

    LoginResponse login(LoginRequest request, String userAgent);

    String refresh(String refreshToken);

    void logout(Long userId);

    List<SysMenu> getMenus(Long userId);
}
