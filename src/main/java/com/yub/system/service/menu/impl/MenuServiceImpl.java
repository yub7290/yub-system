package com.yub.system.service.menu.impl;

import com.yub.system.dto.menu.MenuCreateReqDTO;
import com.yub.system.dto.menu.MenuQueryDTO;
import com.yub.system.dto.menu.MenuUpdateReqDTO;
import com.yub.system.entity.menu.SysMenu;
import com.yub.system.exception.SystemErrorCode;
import com.yub.system.exception.SystemException;
import com.yub.system.mapper.menu.SysMenuMapper;
import com.yub.system.service.menu.SysMenuService;
import com.yub.system.vo.menu.MenuDetailRespVO;
import com.yub.framework.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单服务实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 菜单管理服务实现
 * @Version: 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> selectTree() {
        List<SysMenu> allMenus = sysMenuMapper.selectManagementList();
        return buildMenuTree(allMenus);
    }

    @Override
    public List<SysMenu> selectTreeByCondition(String name, Integer status) {
        MenuQueryDTO query = new MenuQueryDTO();
        query.setName(name);
        query.setStatus(status);
        List<SysMenu> allMenus = sysMenuMapper.selectList(query);
        // 对过滤后的列表仍构建完整树路径（只保留命中节点及其父级路径）
        if (name != null && !name.isEmpty()) {
            return buildFilteredTree(allMenus);
        }
        return buildMenuTree(allMenus);
    }

    @Override
    public MenuDetailRespVO getDetail(Long id) {
        SysMenu menu = sysMenuMapper.selectById(id);
        if (menu == null) {
            throw new SystemException(SystemErrorCode.MENU_NOT_FOUND);
        }

        return MenuDetailRespVO.builder()
                .id(menu.getId())
                .parentId(menu.getParentId())
                .name(menu.getName())
                .path(menu.getPath())
                .component(menu.getComponent())
                .icon(menu.getIcon())
                .sort(menu.getSort())
                .menuType(menu.getMenuType())
                .permission(menu.getPermission())
                .status(menu.getStatus())
                .createTime(menu.getCreateTime())
                .updateTime(menu.getUpdateTime())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(MenuCreateReqDTO dto) {
        // 同级菜单名称唯一性校验
        checkNameUnique(dto.getName(), dto.getParentId(), null);

        SysMenu menu = new SysMenu();
        menu.setParentId(dto.getParentId());
        menu.setName(dto.getName());
        menu.setPath(dto.getPath());
        menu.setComponent(dto.getComponent());
        menu.setIcon(dto.getIcon());
        menu.setSort(dto.getSort() != null ? dto.getSort() : 0);
        menu.setMenuType(dto.getMenuType());
        menu.setPermission(dto.getPermission());
        menu.setStatus(dto.getStatus());
        Long currentUserId = SecurityUtils.getCurrentUserId();
        menu.setCreateBy(currentUserId);
        menu.setUpdateBy(currentUserId);
        sysMenuMapper.insert(menu);
        return menu.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MenuUpdateReqDTO dto) {
        SysMenu menu = sysMenuMapper.selectById(dto.getId());
        if (menu == null) {
            throw new SystemException(SystemErrorCode.MENU_NOT_FOUND);
        }

        // 同级菜单名称唯一性校验（排除自身）
        checkNameUnique(dto.getName(), dto.getParentId(), dto.getId());

        menu.setParentId(dto.getParentId());
        menu.setName(dto.getName());
        menu.setPath(dto.getPath());
        menu.setComponent(dto.getComponent());
        menu.setIcon(dto.getIcon());
        menu.setSort(dto.getSort() != null ? dto.getSort() : 0);
        menu.setMenuType(dto.getMenuType());
        menu.setPermission(dto.getPermission());
        menu.setStatus(dto.getStatus());
        menu.setUpdateBy(SecurityUtils.getCurrentUserId());
        sysMenuMapper.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        SysMenu menu = sysMenuMapper.selectById(id);
        if (menu == null) {
            throw new SystemException(SystemErrorCode.MENU_NOT_FOUND);
        }

        // 检查是否有子菜单
        int childCount = sysMenuMapper.countByParentId(id);
        if (childCount > 0) {
            throw new SystemException(SystemErrorCode.MENU_HAS_CHILDREN);
        }

        sysMenuMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(Long id, Integer status) {
        SysMenu menu = sysMenuMapper.selectById(id);
        if (menu == null) {
            throw new SystemException(SystemErrorCode.MENU_NOT_FOUND);
        }
        sysMenuMapper.updateStatus(id, status);
    }

    /**
     * 校验同级菜单名称唯一性
     */
    private void checkNameUnique(String name, Long parentId, Long excludeId) {
        SysMenu exist = sysMenuMapper.selectByNameAndParentId(name, parentId);
        if (exist != null && (excludeId == null || !exist.getId().equals(excludeId))) {
            throw new SystemException(SystemErrorCode.MENU_NAME_EXISTS);
        }
    }

    /**
     * 构建完整菜单树（递归排序所有层级）
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
     * 构建过滤后的树（保留命中节点及其父级链）
     */
    private List<SysMenu> buildFilteredTree(List<SysMenu> filteredMenus) {
        Map<Long, SysMenu> filteredMap = filteredMenus.stream()
                .collect(Collectors.toMap(SysMenu::getId, m -> m, (a, b) -> a));

        // 批量收集所有祖先节点ID
        List<Long> allAncestorIds = new ArrayList<>();
        collectAncestorIds(filteredMenus, filteredMap, allAncestorIds);

        // 批量查询缺失的祖先节点
        if (!allAncestorIds.isEmpty()) {
            List<SysMenu> ancestors = sysMenuMapper.selectByIds(allAncestorIds);
            for (SysMenu ancestor : ancestors) {
                filteredMap.putIfAbsent(ancestor.getId(), ancestor);
            }
            // 可能还有更上层的祖先需要继续加载
            boolean foundNew = true;
            while (foundNew) {
                foundNew = false;
                List<Long> upperIds = new ArrayList<>();
                for (SysMenu menu : new ArrayList<>(filteredMap.values())) {
                    Long pid = menu.getParentId();
                    if (pid != null && pid != 0L && !filteredMap.containsKey(pid) && !upperIds.contains(pid)) {
                        upperIds.add(pid);
                    }
                }
                if (!upperIds.isEmpty()) {
                    List<SysMenu> upperMenus = sysMenuMapper.selectByIds(upperIds);
                    for (SysMenu m : upperMenus) {
                        filteredMap.putIfAbsent(m.getId(), m);
                    }
                    foundNew = true;
                }
            }
        }

        List<SysMenu> allNodes = new ArrayList<>(filteredMap.values());
        return buildMenuTree(allNodes);
    }

    /**
     * 收集所有祖先节点ID（去重）
     */
    private void collectAncestorIds(List<SysMenu> menus, Map<Long, SysMenu> existingMap, List<Long> result) {
        for (SysMenu menu : menus) {
            Long parentId = menu.getParentId();
            while (parentId != null && parentId != 0L && !existingMap.containsKey(parentId) && !result.contains(parentId)) {
                result.add(parentId);
                // 暂不知道再上一级ID，先停在这里，批量查询后再处理
                break;
            }
        }
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
