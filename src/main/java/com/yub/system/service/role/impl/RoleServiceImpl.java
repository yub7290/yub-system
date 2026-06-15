package com.yub.system.service.role.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.system.dto.role.RoleCreateReqDTO;
import com.yub.system.dto.role.RoleQueryDTO;
import com.yub.system.dto.role.RoleUpdateReqDTO;
import com.yub.system.entity.menu.SysRoleMenu;
import com.yub.system.entity.role.SysRole;
import com.yub.system.exception.SystemErrorCode;
import com.yub.system.exception.SystemException;
import com.yub.system.mapper.role.SysRoleMapper;
import com.yub.system.mapper.role.SysRoleMenuMapper;
import com.yub.system.mapper.user.SysUserRoleMapper;
import com.yub.system.service.role.SysRoleService;
import com.yub.system.vo.role.RoleDetailRespVO;
import com.yub.system.vo.role.RolePageRespVO;
import com.yub.framework.redis.RedisUtils;
import com.yub.framework.security.SecurityUtils;
import com.yub.framework.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 角色管理服务实现
 * @Version: 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements SysRoleService {

    private static final String ROLE_OPTIONS_CACHE_KEY = "system:role:options";

    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysRole> selectAllEnabled() {
        return sysRoleMapper.selectAllEnabled();
    }

    @Override
    public PageResult<RolePageRespVO> page(PageQuery<RoleQueryDTO> pageQuery) {
        RoleQueryDTO queryParam = pageQuery.getQueryParam();
        com.yub.common.model.PageParam pageParam = pageQuery.getPageParam();

        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysRole> list = sysRoleMapper.selectPage(queryParam);
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);

        List<RolePageRespVO> records = BeanUtils.copyList(list, RolePageRespVO.class);
        return PageResult.of(records, pageInfo.getTotal());
    }

    @Override
    public RoleDetailRespVO getDetail(Long id) {
        SysRole role = sysRoleMapper.selectById(id);
        if (role == null) {
            throw new SystemException(SystemErrorCode.ACCOUNT_NOT_FOUND);
        }
        List<Long> menuIds = sysRoleMenuMapper.selectMenuIdsByRoleId(id);

        return RoleDetailRespVO.builder()
                .id(role.getId())
                .name(role.getName())
                .code(role.getCode())
                .sort(role.getSort())
                .status(role.getStatus())
                .description(role.getDescription())
                .menuIds(menuIds)
                .createTime(role.getCreateTime())
                .updateTime(role.getUpdateTime())
                .createBy(role.getCreateBy())
                .updateBy(role.getUpdateBy())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(RoleCreateReqDTO dto) {
        SysRole exist = sysRoleMapper.selectByCode(dto.getCode());
        if (exist != null) {
            throw new SystemException(SystemErrorCode.ROLE_CODE_EXISTS);
        }

        SysRole role = new SysRole();
        role.setName(dto.getName());
        role.setCode(dto.getCode());
        role.setSort(dto.getSort() != null ? dto.getSort() : 0);
        role.setStatus(dto.getStatus());
        role.setDescription(dto.getDescription());
        Long currentUserId = SecurityUtils.getCurrentUserId();
        role.setCreateBy(currentUserId);
        role.setUpdateBy(currentUserId);
        sysRoleMapper.insert(role);
        Long roleId = role.getId();

        insertRoleMenus(roleId, dto.getMenuIds());
        evictRoleOptionsCache();

        return roleId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RoleUpdateReqDTO dto) {
        SysRole role = sysRoleMapper.selectById(dto.getId());
        if (role == null) {
            throw new SystemException(SystemErrorCode.ACCOUNT_NOT_FOUND);
        }

        role.setName(dto.getName());
        role.setSort(dto.getSort() != null ? dto.getSort() : 0);
        role.setStatus(dto.getStatus());
        role.setDescription(dto.getDescription());
        role.setUpdateBy(SecurityUtils.getCurrentUserId());
        sysRoleMapper.updateById(role);

        // 增量更新菜单权限：先删后插
        sysRoleMenuMapper.deleteByRoleId(dto.getId());
        insertRoleMenus(dto.getId(), dto.getMenuIds());

        evictRoleOptionsCache();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        List<Long> userIds = sysUserRoleMapper.selectUserIdsByRoleId(id);
        if (!userIds.isEmpty()) {
            throw new SystemException(SystemErrorCode.ROLE_HAS_USERS);
        }

        sysRoleMapper.deleteById(id);
        sysRoleMenuMapper.deleteByRoleId(id);
        evictRoleOptionsCache();
    }

    @Override
    public void changeStatus(Long id, Integer status) {
        SysRole role = sysRoleMapper.selectById(id);
        if (role == null) {
            throw new SystemException(SystemErrorCode.ACCOUNT_NOT_FOUND);
        }
        sysRoleMapper.updateStatus(id, status);
        evictRoleOptionsCache();
    }

    private void insertRoleMenus(Long roleId, List<Long> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) {
            return;
        }
        List<SysRoleMenu> list = menuIds.stream()
                .map(menuId -> {
                    SysRoleMenu rm = new SysRoleMenu();
                    rm.setRoleId(roleId);
                    rm.setMenuId(menuId);
                    return rm;
                })
                .collect(Collectors.toList());
        sysRoleMenuMapper.insertBatch(list);
    }

    private void evictRoleOptionsCache() {
        RedisUtils.delete(ROLE_OPTIONS_CACHE_KEY);
    }
}
