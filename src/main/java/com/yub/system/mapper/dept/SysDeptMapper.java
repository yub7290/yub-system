package com.yub.system.mapper.dept;

import com.yub.system.dto.dept.DeptQueryDTO;
import com.yub.system.entity.dept.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 部门数据访问层
 * @Version: 1.0.0
 */
@Mapper
public interface SysDeptMapper {

    /**
     * 查询所有部门列表（管理端用，忽略状态）
     *
     * @return 部门列表
     */
    List<SysDept> selectTree();

    /**
     * 查询部门列表（按条件）
     *
     * @param query 查询参数（名称、状态）
     * @return 部门列表
     */
    List<SysDept> selectList(@Param("query") DeptQueryDTO query);

    /**
     * 根据ID查询部门
     *
     * @param id 部门ID
     * @return 部门
     */
    SysDept selectById(@Param("id") Long id);

    /**
     * 根据编码查询部门（含已删除，用于唯一性校验）
     *
     * @param deptCode 部门编码
     * @return 部门
     */
    SysDept selectByCode(@Param("deptCode") String deptCode);

    /**
     * 根据名称和父ID查询部门（同级同名唯一性校验）
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 部门
     */
    SysDept selectByNameAndParentId(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 查询子部门数量
     *
     * @param parentId 父部门ID
     * @return 子部门数量
     */
    int countByParentId(@Param("parentId") Long parentId);

    /**
     * 新增部门
     *
     * @param dept 部门
     * @return 影响行数
     */
    int insert(SysDept dept);

    /**
     * 更新部门
     *
     * @param dept 部门
     * @return 影响行数
     */
    int updateById(SysDept dept);

    /**
     * 逻辑删除部门
     *
     * @param id 部门ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 更新部门状态
     *
     * @param id     部门ID
     * @param status 状态（1=启用 0=禁用）
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
