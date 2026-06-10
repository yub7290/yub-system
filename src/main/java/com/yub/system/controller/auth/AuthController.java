package com.yub.system.controller.auth;

import com.yub.common.model.Response;
import com.yub.framework.util.IpUtils;
import com.yub.system.dto.auth.LoginRequest;
import com.yub.system.dto.auth.LoginResponse;
import com.yub.system.entity.menu.SysMenu;
import com.yub.system.service.auth.AuthService;
import com.yub.system.service.auth.CaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 登录认证
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 登录认证接口
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CaptchaService captchaService;

    /**
     * 获取验证码
     * @return 验证码
     */
    @GetMapping("/captcha")
    public Response<Map<String, String>> captcha(HttpServletRequest request, HttpServletResponse response) {
        return Response.success(captchaService.generate(request, response));
    }

    /**
     * 登录
     * @param request 登录请求
     * @param httpRequest HTTP请求
     * @return 登录响应
     */
    @PostMapping("/login")
    public Response<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                          HttpServletRequest httpRequest) {

        String userAgent = httpRequest.getHeader("User-Agent");
        LoginResponse response = authService.login(request, userAgent);
        return Response.success(response);
    }

    /**
     * 刷新Token
     * @param body 请求体
     * @return 新的Token
     */
    @PostMapping("/refresh")
    public Response<Map<String, String>> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        String accessToken = authService.refresh(refreshToken);
        return Response.success(Map.of("accessToken", accessToken));
    }

    /**
     * 登出
     * @return 登出响应
     */
    @PostMapping("/logout")
    public Response<Void> logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(auth.getName());
        authService.logout(userId);
        return Response.success();
    }

    /**
     * 获取菜单
     * @return 菜单
     */
    @GetMapping("/menus")
    public Response<List<SysMenu>> menus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(auth.getName());
        return Response.success(authService.getMenus(userId));
    }
}
