package com.yub.system.service.dept.impl;

import com.yub.system.dto.dept.DeptCreateReqDTO;
import com.yub.system.dto.dept.DeptQueryDTO;
import com.yub.system.dto.dept.DeptUpdateReqDTO;
import com.yub.system.entity.dept.SysDept;
import com.yub.system.exception.SystemErrorCode;
import com.yub.system.exception.SystemException;
import com.yub.system.mapper.dept.SysDeptMapper;
import com.yub.system.mapper.user.SysUserMapper;
import com.yub.system.service.dept.SysDeptService;
import com.yub.system.vo.dept.DeptDetailRespVO;
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
 * 部门服务实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 部门管理服务实现
 * @Version: 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;
    // 直接依赖 SysUserMapper 查询部门下用户数（同模块内，避免接口膨胀）
    private final SysUserMapper sysUserMapper;

    @Override
    public List<SysDept> selectTree() {
        List<SysDept> allDepts = sysDeptMapper.selectTree();
        return buildDeptTree(allDepts);
    }

    @Override
    public List<SysDept> selectTreeByCondition(DeptQueryDTO query) {
        List<SysDept> allDepts = sysDeptMapper.selectList(query);
        // 有过滤条件时使用 buildFilteredTree 补全祖先节点路径
        boolean hasFilter = (query.getDeptName() != null && !query.getDeptName().isEmpty())
                || query.getStatus() != null;
        if (hasFilter) {
            return buildFilteredTree(allDepts);
        }
        return buildDeptTree(allDepts);
    }

    @Override
    public DeptDetailRespVO getDetail(Long id) {
        SysDept dept = sysDeptMapper.selectById(id);
        if (dept == null) {
            throw new SystemException(SystemErrorCode.DEPT_NOT_FOUND);
        }

        String parentName = null;
        if (dept.getParentId() != null && dept.getParentId() != 0L) {
            SysDept parent = sysDeptMapper.selectById(dept.getParentId());
            if (parent != null) {
                parentName = parent.getDeptName();
            }
        }

        return DeptDetailRespVO.builder()
                .id(dept.getId())
                .deptName(dept.getDeptName())
                .deptCode(dept.getDeptCode())
                .parentId(dept.getParentId())
                .parentName(parentName)
                .sort(dept.getSort())
                .status(dept.getStatus())
                .createTime(dept.getCreateTime())
                .updateTime(dept.getUpdateTime())
                .createBy(dept.getCreateBy())
                .updateBy(dept.getUpdateBy())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(DeptCreateReqDTO dto) {
        // 同级部门名称唯一性校验
        checkNameUnique(dto.getDeptName(), dto.getParentId(), null);

        // 部门编码唯一性校验（非空时）
        if (dto.getDeptCode() != null && !dto.getDeptCode().isEmpty()) {
            checkCodeUnique(dto.getDeptCode(), null);
        }

        SysDept dept = new SysDept();
        dept.setParentId(dto.getParentId());
        dept.setDeptName(dto.getDeptName());
        dept.setDeptCode(dto.getDeptCode());
        dept.setSort(dto.getSort() != null ? dto.getSort() : 0);
        dept.setStatus(dto.getStatus());
        Long currentUserId = SecurityUtils.getCurrentUserId();
        dept.setCreateBy(currentUserId);
        dept.setUpdateBy(currentUserId);
        sysDeptMapper.insert(dept);
        return dept.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DeptUpdateReqDTO dto) {
        SysDept dept = sysDeptMapper.selectById(dto.getId());
        if (dept == null) {
            throw new SystemException(SystemErrorCode.DEPT_NOT_FOUND);
        }

        // 同级部门名称唯一性校验（排除自身）
        checkNameUnique(dto.getDeptName(), dto.getParentId(), dto.getId());

        // 部门编码唯一性校验（非空时，排除自身）
        if (dto.getDeptCode() != null && !dto.getDeptCode().isEmpty()) {
            checkCodeUnique(dto.getDeptCode(), dto.getId());
        }

        dept.setParentId(dto.getParentId());
        dept.setDeptName(dto.getDeptName());
        dept.setDeptCode(dto.getDeptCode());
        dept.setSort(dto.getSort() != null ? dto.getSort() : 0);
        dept.setStatus(dto.getStatus());
        dept.setUpdateBy(SecurityUtils.getCurrentUserId());
        sysDeptMapper.updateById(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        SysDept dept = sysDeptMapper.selectById(id);
        if (dept == null) {
            throw new SystemException(SystemErrorCode.DEPT_NOT_FOUND);
        }

        // 检查是否有子部门
        int childCount = sysDeptMapper.countByParentId(id);
        if (childCount > 0) {
            throw new SystemException(SystemErrorCode.DEPT_HAS_CHILDREN);
        }

        // 检查部门下是否有用户
        int userCount = sysUserMapper.countByDeptId(id);
        if (userCount > 0) {
            throw new SystemException(SystemErrorCode.DEPT_HAS_USERS);
        }

        sysDeptMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(Long id, Integer status) {
        SysDept dept = sysDeptMapper.selectById(id);
        if (dept == null) {
            throw new SystemException(SystemErrorCode.DEPT_NOT_FOUND);
        }
        sysDeptMapper.updateStatus(id, status);
    }

    /**
     * 校验同级部门名称唯一性
     */
    private void checkNameUnique(String deptName, Long parentId, Long excludeId) {
        SysDept exist = sysDeptMapper.selectByNameAndParentId(deptName, parentId);
        if (exist != null && (excludeId == null || !exist.getId().equals(excludeId))) {
            throw new SystemException(SystemErrorCode.DEPT_NAME_EXISTS);
        }
    }

    /**
     * 校验部门编码唯一性
     */
    private void checkCodeUnique(String deptCode, Long excludeId) {
        SysDept exist = sysDeptMapper.selectByCode(deptCode);
        if (exist != null && (excludeId == null || !exist.getId().equals(excludeId))) {
            throw new SystemException(SystemErrorCode.DEPT_CODE_EXISTS);
        }
    }

    /**
     * 构建完整部门树（递归排序所有层级）
     */
    private List<SysDept> buildDeptTree(List<SysDept> depts) {
        Map<Long, SysDept> deptMap = depts.stream()
                .collect(Collectors.toMap(SysDept::getId, d -> d, (a, b) -> a));
        List<SysDept> roots = new ArrayList<>();
        for (SysDept dept : depts) {
            if (dept.getParentId() == null || dept.getParentId() == 0L) {
                roots.add(dept);
            } else {
                SysDept parent = deptMap.get(dept.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(dept);
                }
            }
        }
        sortDeptsRecursively(roots);
        return roots;
    }

    /**
     * 构建过滤后的树（保留命中节点及其父级链）
     */
    private List<SysDept> buildFilteredTree(List<SysDept> filteredDepts) {
        Map<Long, SysDept> filteredMap = filteredDepts.stream()
                .collect(Collectors.toMap(SysDept::getId, d -> d, (a, b) -> a));

        // 批量收集所有祖先节点ID
        List<Long> allAncestorIds = new ArrayList<>();
        for (SysDept dept : filteredDepts) {
            Long parentId = dept.getParentId();
            if (parentId != null && parentId != 0L && !filteredMap.containsKey(parentId) && !allAncestorIds.contains(parentId)) {
                allAncestorIds.add(parentId);
            }
        }

        // 批量查询缺失的祖先节点，支持多级
        if (!allAncestorIds.isEmpty()) {
            List<Long> ancestorIds = new ArrayList<>(allAncestorIds);
            boolean foundNew = true;
            int maxDepth = 10;
            int depth = 0;
            while (foundNew && depth < maxDepth) {
                foundNew = false;
                depth++;
                List<Long> toQuery = new ArrayList<>(ancestorIds);
                for (Long pid : toQuery) {
                    SysDept ancestor = sysDeptMapper.selectById(pid);
                    if (ancestor != null) {
                        filteredMap.putIfAbsent(ancestor.getId(), ancestor);
                        Long upperPid = ancestor.getParentId();
                        if (upperPid != null && upperPid != 0L && !filteredMap.containsKey(upperPid) && !ancestorIds.contains(upperPid)) {
                            ancestorIds.add(upperPid);
                            foundNew = true;
                        }
                    }
                }
            }
        }

        List<SysDept> allNodes = new ArrayList<>(filteredMap.values());
        return buildDeptTree(allNodes);
    }

    /**
     * 递归排序部门树
     */
    private void sortDeptsRecursively(List<SysDept> depts) {
        depts.sort(Comparator.comparing(SysDept::getSort, Comparator.nullsLast(Comparator.naturalOrder())));
        depts.forEach(dept -> {
            if (dept.getChildren() != null && !dept.getChildren().isEmpty()) {
                sortDeptsRecursively(dept.getChildren());
            }
        });
    }
}
