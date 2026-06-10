package com.yub.system.service.auth.impl;

import com.yub.framework.security.JwtProvider;
import com.yub.system.dto.auth.LoginRequest;
import com.yub.system.dto.auth.LoginResponse;
import com.yub.system.entity.log.SysAccessLog;
import com.yub.system.entity.menu.SysMenu;
import com.yub.system.entity.user.SysUser;
import com.yub.system.exception.SystemErrorCode;
import com.yub.system.exception.SystemException;
import com.yub.system.mapper.log.SysAccessLogMapper;
import com.yub.system.mapper.menu.SysMenuMapper;
import com.yub.system.mapper.user.SysUserMapper;
import com.yub.system.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证服务实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 认证服务实现
 * @Version: 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper sysUserMapper;
    private final SysMenuMapper sysMenuMapper;
    private final SysAccessLogMapper sysAccessLogMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedissonClient redissonClient;

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private static final String CAPTCHA_PREFIX = "captcha:";

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request, String userAgent) {
        String captchaKey = CAPTCHA_PREFIX + request.getCaptchaKey();
        RBucket<String> captchaBucket = redissonClient.getBucket(captchaKey);
        String storedCode = captchaBucket.get();
        if (StringUtils.isBlank(storedCode) || !storedCode.equalsIgnoreCase(request.getCaptchaCode())) {
            throw new SystemException(SystemErrorCode.CAPTCHA_ERROR);
        }
        captchaBucket.delete();

        SysUser user = sysUserMapper.selectByAccount(request.getAccount());
        if (user == null) {
            throw new SystemException(SystemErrorCode.PASSWORD_ERROR);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new SystemException(SystemErrorCode.PASSWORD_ERROR);
        }

        if (user.getStatus() == 0) {
            throw new SystemException(SystemErrorCode.ACCOUNT_DISABLED);
        }

        List<String> roles = sysUserMapper.selectRoleCodesByUserId(user.getId());
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getAccount(), claims);
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());

        String refreshKey = REFRESH_TOKEN_PREFIX + user.getId();
        RBucket<String> refreshBucket = redissonClient.getBucket(refreshKey);
        refreshBucket.set(refreshToken, Duration.ofDays(7));

        List<SysMenu> menus;
        try {
            menus = getMenus(user.getId());
        } catch (Exception e) {
            log.warn("菜单查询失败", e);
            menus = List.of();
        }

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userInfo(LoginResponse.UserInfo.builder()
                        .id(user.getId())
                        .account(user.getAccount())
                        .nickName(user.getNickName())
                        .avatarUrl(user.getAvatarUrl())
                        .build())
                .menus(menus)
                .build();
    }

    @Override
    public String refresh(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new SystemException(SystemErrorCode.TOKEN_EXPIRED);
        }
        String userId = jwtProvider.getUserId(refreshToken);
        String refreshKey = REFRESH_TOKEN_PREFIX + userId;
        RBucket<String> bucket = redissonClient.getBucket(refreshKey);
        String storedToken = bucket.get();
        if (storedToken == null || !storedToken.equals(refreshToken)) {
            throw new SystemException(SystemErrorCode.TOKEN_INVALID);
        }
        SysUser user = sysUserMapper.selectById(Long.valueOf(userId));
        if (user == null || user.getStatus() == 0) {
            throw new SystemException(SystemErrorCode.ACCOUNT_DISABLED);
        }
        List<String> roles = sysUserMapper.selectRoleCodesByUserId(user.getId());
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return jwtProvider.generateAccessToken(user.getId(), user.getAccount(), claims);
    }

    @Override
    public void logout(Long userId) {
        String refreshKey = REFRESH_TOKEN_PREFIX + userId;
        redissonClient.getBucket(refreshKey).delete();
    }

    @Override
    public List<SysMenu> getMenus(Long userId) {
        List<SysMenu> allMenus = sysMenuMapper.selectByUserId(userId);
        return buildMenuTree(allMenus, 0L);
    }

    private List<SysMenu> buildMenuTree(List<SysMenu> menus, Long parentId) {
        List<SysMenu> tree = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menu.getParentId().equals(parentId)) {
                menu.setChildren(buildMenuTree(menus, menu.getId()));
                tree.add(menu);
            }
        }
        return tree;
    }

    private void recordLoginLog(Long userId, String account, String ip,
                                String userAgent, int status, String message) {
        SysAccessLog log = new SysAccessLog();
        log.setUserId(userId);
        log.setNickName(account);
        log.setIp(ip);
        log.setUserAgent(userAgent);
        log.setStatus(status);
        log.setErrorMsg(message);
        sysAccessLogMapper.insert(log);
    }
}
