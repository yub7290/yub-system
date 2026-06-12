package com.yub.system.service.auth.impl;

import com.yub.common.constant.RedisKeyConstants;
import com.yub.common.enums.StatusEnum;
import com.yub.framework.security.JwtProvider;
import com.yub.framework.redis.RedisUtils;
import com.yub.system.dto.auth.LoginReqDTO;
import com.yub.system.entity.menu.SysMenu;
import com.yub.system.entity.user.SysUser;
import com.yub.system.exception.SystemErrorCode;
import com.yub.system.exception.SystemException;
import com.yub.system.mapper.menu.SysMenuMapper;
import com.yub.system.mapper.user.SysUserMapper;
import com.yub.system.service.auth.AuthService;
import com.yub.system.vo.auth.LoginRespVO;
import com.yub.system.vo.auth.UserInfoRespVO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    /** 超级管理员用户ID */
    private static final Long SUPER_ADMIN_ID = 1L;

    /**
     * 登录认证：验证码校验→账号密码校验→账号状态检查→生成JWT→记录登录信息
     * <p>注意：@Transactional 仅覆盖DB操作，Redis操作采用最终一致性策略
     */
    @Override
    public LoginRespVO login(LoginReqDTO request, String ip) {
        String captchaKey = RedisKeyConstants.CAPTCHA_PREFIX + request.getCaptchaKey();
        String storedCode = RedisUtils.get(captchaKey).orElse("").toString();
        if (StringUtils.isBlank(storedCode) || !storedCode.equalsIgnoreCase(request.getCaptchaCode())) {
            throw new SystemException(SystemErrorCode.CAPTCHA_ERROR);
        }
        RedisUtils.delete(captchaKey);

        SysUser user = sysUserMapper.selectByAccount(request.getAccount());
        if (user == null) {
            throw new SystemException(SystemErrorCode.PASSWORD_ERROR);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new SystemException(SystemErrorCode.PASSWORD_ERROR);
        }

        if (StatusEnum.isDisabled(user.getStatus())) {
            throw new SystemException(SystemErrorCode.ACCOUNT_DISABLED);
        }

        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getAccount(), new HashMap<>());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());

        String refreshSetKey = RedisKeyConstants.REFRESH_TOKEN_PREFIX + user.getId();
        RedisUtils.addToSet(refreshSetKey, refreshToken);
        RedisUtils.expireSet(refreshSetKey, Duration.ofDays(7));

        LoginRespVO loginRespDTO = LoginRespVO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        sysUserMapper.updateLoginCountAtomic(user.getId(), ip);

        return loginRespDTO;
    }

    /**
     * 刷新Token（Refresh Token Rotation）：校验旧Token→生成新AccessToken+RefreshToken→替换Redis中的旧RefreshToken
     *
     * @param refreshToken 当前RefreshToken
     * @return 包含新accessToken和refreshToken
     */
    @Override
    public LoginRespVO refresh(String refreshToken) {
        // 使用 parseTokenIfValid 一次解析 Token，避免重复签名验证
        Claims claims = jwtProvider.parseTokenIfValid(refreshToken);
        if (claims == null) {
            throw new SystemException(SystemErrorCode.TOKEN_EXPIRED);
        }
        String userId = claims.getSubject();
        String refreshSetKey = RedisKeyConstants.REFRESH_TOKEN_PREFIX + userId;
        String lockKey = "lock:" + refreshSetKey;
        RedisUtils.lock(lockKey);
        try {
            if (!RedisUtils.setContains(refreshSetKey, refreshToken)) {
                throw new SystemException(SystemErrorCode.TOKEN_INVALID);
            }
            SysUser user = sysUserMapper.selectById(Long.valueOf(userId));
            if (user == null || StatusEnum.isDisabled(user.getStatus())) {
                throw new SystemException(SystemErrorCode.ACCOUNT_DISABLED);
            }
            String newAccessToken = jwtProvider.generateAccessToken(user.getId(), user.getAccount(), new HashMap<>());
            String newRefreshToken = jwtProvider.generateRefreshToken(user.getId());
            RedisUtils.removeFromSet(refreshSetKey, refreshToken);
            RedisUtils.addToSet(refreshSetKey, newRefreshToken);
            RedisUtils.expireSet(refreshSetKey, Duration.ofDays(7));

            return LoginRespVO.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        } finally {
            RedisUtils.unlock(lockKey);
        }
    }

    /**
     * 登出：删除RefreshToken，将AccessToken加入黑名单（TTL=AccessToken剩余有效期）
     *
     * @param userId      用户ID
     * @param accessToken 当前AccessToken
     */
    @Override
    public void logout(Long userId, String accessToken) {
        String refreshSetKey = RedisKeyConstants.REFRESH_TOKEN_PREFIX + userId;
        String lockKey = "lock:" + refreshSetKey;
        RedisUtils.lock(lockKey);
        try {
            RedisUtils.deleteSet(refreshSetKey);

            // 使用 parseTokenIfValid 一次解析即可，避免重复签名验证
            Claims claims = jwtProvider.parseTokenIfValid(accessToken);
            if (claims != null) {
                long remaining = claims.getExpiration().getTime() - System.currentTimeMillis();
                if (remaining > 0) {
                    String tokenHash = DigestUtils.sha256Hex(accessToken);
                    RedisUtils.set(RedisKeyConstants.TOKEN_BLACKLIST_PREFIX + tokenHash, "1", Duration.ofMillis(remaining));
                }
            }
        } finally {
            RedisUtils.unlock(lockKey);
        }
    }

    /**
     * 获取当前用户信息：userId=1为超级管理员拥有全部权限
     */
    @Override
    public UserInfoRespVO getCurrentUserInfo(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new SystemException(SystemErrorCode.ACCOUNT_NOT_FOUND);
        }

        UserInfoRespVO.UserVO userVO = UserInfoRespVO.UserVO.builder()
                .id(user.getId())
                .account(user.getAccount())
                .nickName(user.getNickName())
                .avatarUrl(user.getAvatarUrl())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();

        List<String> roles = sysUserMapper.selectRoleCodesByUserId(userId);

        List<SysMenu> allMenus;
        if (SUPER_ADMIN_ID.equals(userId)) {
            allMenus = sysMenuMapper.selectAll();
        } else {
            allMenus = sysMenuMapper.selectByUserId(userId);
        }

        List<SysMenu> menuTree = buildMenuTree(allMenus);

        List<String> permissions = allMenus.stream()
                .map(SysMenu::getPermission)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .toList();

        return UserInfoRespVO.builder()
                .user(userVO)
                .roles(roles)
                .menus(menuTree)
                .permissions(permissions)
                .build();
    }

    /**
     * 构建菜单树（递归排序所有层级）
     */
    private List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        Map<Long, SysMenu> menuMap = menus.stream()
                .collect(Collectors.toMap(SysMenu::getId, m -> m, (a, b) -> a));
        List<SysMenu> roots = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menu.getParentId() == null || menu.getParentId() == 0L) {
                roots.add(menu);
            } else {
                SysMenu parent = menuMap.get(menu.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(menu);
                }
            }
        }
        sortMenusRecursively(roots);
        return roots;
    }

    /**
     * 递归排序菜单树
     */
    private void sortMenusRecursively(List<SysMenu> menus) {
        menus.sort(Comparator.comparing(SysMenu::getSort, Comparator.nullsLast(Comparator.naturalOrder())));
        menus.forEach(menu -> {
            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                sortMenusRecursively(menu.getChildren());
            }
        });
    }
}
