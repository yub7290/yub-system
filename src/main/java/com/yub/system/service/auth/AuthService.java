package com.yub.system.service.auth;

import com.yub.system.dto.auth.LoginReqDTO;
import com.yub.system.vo.auth.LoginRespVO;
import com.yub.system.vo.auth.UserInfoRespVO;
import com.yub.system.entity.menu.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * 认证服务接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 认证服务接口
 * @Version: 1.0.0
 */
public interface AuthService {

    /**
     * 用户登录认证
     *
     * @param request   登录请求
     * @param ip        客户端IP
     * @return 登录响应（含Token和用户信息）
     */
    LoginRespVO login(LoginReqDTO request, String ip);

    /**
     * 刷新Token（Refresh Token Rotation），旧RefreshToken失效并签发新Token
     *
     * @param refreshToken 当前RefreshToken
     * @return 包含新accessToken和refreshToken的Map
     */
    LoginRespVO refresh(String refreshToken);

    /**
     * 登出：将RefreshToken和AccessToken加入黑名单
     *
     * @param userId      用户ID
     * @param accessToken 当前AccessToken（加入黑名单）
     */
    void logout(Long userId, String accessToken);

    /**
     * 获取当前用户信息（用户基本信息、角色、菜单树、权限标识）
     *
     * @param userId 当前用户ID
     * @return 用户信息响应
     */
    UserInfoRespVO getCurrentUserInfo(Long userId);
}
