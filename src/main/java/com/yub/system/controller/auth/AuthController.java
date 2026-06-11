package com.yub.system.controller.auth;

import com.yub.common.model.Response;
import com.yub.framework.security.JwtProvider;
import com.yub.framework.security.SecurityUtils;
import com.yub.framework.util.IpUtils;
import com.yub.system.dto.auth.LoginReqDTO;
import com.yub.system.service.auth.AuthService;
import com.yub.system.service.auth.CaptchaService;
import com.yub.system.vo.auth.CaptchaRespVO;
import com.yub.system.vo.auth.LoginRespVO;
import com.yub.system.vo.auth.UserInfoRespVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    private final JwtProvider jwtProvider;

    /**
     * 获取验证码
     *
     * @return 验证码
     */
    @GetMapping("/captcha")
    public Response<CaptchaRespVO> captcha() {
        return Response.success(captchaService.generate());
    }

    /**
     * 登录
     *
     * @param reqDTO      登录请求
     * @param httpRequest HTTP请求
     * @return 登录响应
     */
    @PostMapping("/login")
    public Response<LoginRespVO> login(@Valid @RequestBody LoginReqDTO reqDTO,
                                       HttpServletRequest httpRequest) {
        String ip = IpUtils.getClientIp(httpRequest);
        LoginRespVO response = authService.login(reqDTO, ip);
        return Response.success(response);
    }

    /**
     * 刷新Token
     *
     * @param refreshToken 刷新Token
     */
    @PostMapping("/refresh/{refreshToken}")
    public Response<LoginRespVO> refresh(@PathVariable String refreshToken) {
        return Response.success(authService.refresh(refreshToken));
    }

    /**
     * 登出：清除RefreshToken并将AccessToken加入黑名单
     *
     * @param request HTTP请求（用于提取AccessToken）
     * @return 登出响应
     */
    @PostMapping("/logout")
    public Response<Void> logout(HttpServletRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        String accessToken = jwtProvider.getToken(request);
        authService.logout(userId, accessToken);
        return Response.success();
    }

    /**
     * 获取当前用户信息（含角色、菜单树、权限标识）
     *
     * @return 用户信息响应
     */
    @GetMapping("/getUserInfo")
    public Response<UserInfoRespVO> getUserInfo() {
        Long userId = SecurityUtils.getCurrentUserId();
        return Response.success(authService.getCurrentUserInfo(userId));
    }
}
