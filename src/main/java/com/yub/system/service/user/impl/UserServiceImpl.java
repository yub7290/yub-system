package com.yub.system.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.framework.security.SecurityUtils;
import com.yub.framework.security.UserDetailsLoader;
import com.yub.framework.util.BeanUtils;
import com.yub.system.dto.user.UserCreateReqDTO;
import com.yub.system.dto.user.UserQueryDTO;
import com.yub.system.dto.user.UserUpdateReqDTO;
import com.yub.system.entity.user.SysUser;
import com.yub.system.entity.user.SysUserRole;
import com.yub.system.exception.SystemErrorCode;
import com.yub.system.exception.SystemException;
import com.yub.system.mapper.param.SysParamMapper;
import com.yub.system.mapper.user.SysUserMapper;
import com.yub.system.mapper.user.SysUserRoleMapper;
import com.yub.system.service.user.UserService;
import com.yub.system.vo.user.UserDetailRespVO;
import com.yub.system.vo.user.UserPageRespVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * 用户管理 Service 实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 用户管理业务实现
 * @Version: 1.0.1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * SM3("admin123") — 前端SM3哈希后的默认密码值
     * 登录时前端发送 SM3(password)，后端 BCrypt 比对，
     * 因此重置密码时需存储 BCrypt(SM3(password)) 以保持链路一致。
     */
    private static final String DEFAULT_PASSWORD_SM3 = "667c756cf9334e328a56e44e906245c8e214c655a160f18fdb84d79c209c49cf";

    /** 超级管理员用户ID（不允许删除/禁用/修改） */
    private static final Long SUPER_ADMIN_ID = 1L;

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysParamMapper sysParamMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsLoader userDetailsLoader;

    @Override
    public PageResult<UserPageRespVO> page(PageQuery<UserQueryDTO> pageQuery) {
        PageHelper.startPage(pageQuery.getPageParam().getPageNum(), pageQuery.getPageParam().getPageSize());
        List<SysUser> list = sysUserMapper.select(pageQuery.getQueryParam());
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);

        List<UserPageRespVO> records = BeanUtils.copyList(list, UserPageRespVO.class);

        return PageResult.of(records, pageInfo.getTotal());
    }

    @Override
    public UserDetailRespVO getDetail(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new SystemException(SystemErrorCode.ACCOUNT_NOT_FOUND);
        }

        List<Long> roleIds = sysUserRoleMapper.selectRoleIdsByUserId(id);

        return UserDetailRespVO.builder()
                .id(user.getId())
                .account(user.getAccount())
                .nickName(user.getNickName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .avatarUrl(user.getAvatarUrl())
                .status(user.getStatus())
                .roleIds(roleIds)
                .createTime(user.getCreateTime())
                .updateTime(user.getUpdateTime())
                .createBy(user.getCreateBy())
                .updateBy(user.getUpdateBy())
                .lastLoginTime(user.getLastLoginTime())
                .lastLoginIp(user.getLastLoginIp())
                .loginCount(user.getLoginCount())
                .build();
    }

    @Override
    @Transactional
    public void create(UserCreateReqDTO req) {
        SysUser exist = sysUserMapper.selectByAccountIncludeDeleted(req.getAccount());
        if (exist != null) {
            throw new SystemException(SystemErrorCode.ACCOUNT_EXISTS);
        }

        SysUser user = new SysUser();
        user.setAccount(req.getAccount());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setNickName(req.getNickName());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());
        user.setStatus(req.getStatus());
        user.setCreateBy(SecurityUtils.getCurrentUserId());
        user.setUpdateBy(SecurityUtils.getCurrentUserId());
        sysUserMapper.insert(user);

        if (req.getRoleIds() != null && !req.getRoleIds().isEmpty()) {
            List<SysUserRole> roles = req.getRoleIds().stream()
                    .map(roleId -> {
                        SysUserRole ur = new SysUserRole();
                        ur.setUserId(user.getId());
                        ur.setRoleId(roleId);
                        return ur;
                    })
                    .toList();
            sysUserRoleMapper.insertBatch(roles);
        }
    }

    @Override
    @Transactional
    public void update(UserUpdateReqDTO req) {
        SysUser user = new SysUser();
        user.setId(req.getId());
        user.setNickName(req.getNickName());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());
        user.setStatus(req.getStatus());
        user.setUpdateBy(SecurityUtils.getCurrentUserId());
        sysUserMapper.updateById(user);

        // Only update roles if changed (order-independent comparison)
        List<Long> oldRoleIds = sysUserRoleMapper.selectRoleIdsByUserId(req.getId());
        if (req.getRoleIds() != null && !new HashSet<>(req.getRoleIds()).equals(new HashSet<>(oldRoleIds))) {
            sysUserRoleMapper.deleteByUserId(req.getId());
            if (!req.getRoleIds().isEmpty()) {
                List<SysUserRole> roles = req.getRoleIds().stream()
                        .map(roleId -> {
                            SysUserRole ur = new SysUserRole();
                            ur.setUserId(req.getId());
                            ur.setRoleId(roleId);
                            return ur;
                        })
                        .toList();
                sysUserRoleMapper.insertBatch(roles);
            }
        }
        userDetailsLoader.evictRoleCache(req.getId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (SUPER_ADMIN_ID.equals(id)) {
            throw new SystemException(SystemErrorCode.SUPER_ADMIN_DELETE);
        }
        sysUserMapper.deleteById(id);
        sysUserRoleMapper.deleteByUserId(id);
    }

    @Override
    @Transactional
    public void resetPassword(Long id) {
        if (SUPER_ADMIN_ID.equals(id)) {
            throw new SystemException(SystemErrorCode.SUPER_ADMIN_RESET_PASSWORD);
        }
        // 系统参数值应为预计算的 SM3 哈希（与前端 SM3 哈希链路保持一致）
        String paramValue = sysParamMapper.selectValueByCode("user.default.password");
        if (StringUtils.isNotBlank(paramValue)) {
            sysUserMapper.updatePassword(id, passwordEncoder.encode(paramValue));
            log.info("用户 {} 密码已重置（使用系统参数配置）", id);
        } else {
            // 使用默认密码 admin123 的 SM3 哈希值 -> BCrypt(SM3("admin123"))
            sysUserMapper.updatePassword(id, passwordEncoder.encode(DEFAULT_PASSWORD_SM3));
            log.info("用户 {} 密码已重置为默认密码（admin123）", id);
        }
    }

    @Override
    @Transactional
    public void changeStatus(Long id, Integer status) {
        if (SUPER_ADMIN_ID.equals(id)) {
            throw new SystemException(SystemErrorCode.SUPER_ADMIN_DISABLE);
        }
        sysUserMapper.updateStatus(id, status);
    }

}
