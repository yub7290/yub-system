package com.yub.system.mapper.role;

import com.yub.system.dto.role.RoleQueryDTO;
import com.yub.system.entity.role.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 角色数据访问层
 * @Version: 1.0.0
 */
@Mapper
public interface SysRoleMapper {

    /**
     * 查询所有启用角色（供下拉框使用）
     *
     * @return 角色列表
     */
    List<SysRole> selectAllEnabled();

    /**
     * 分页查询角色列表
     *
     * @param query 查询参数
     * @return 角色列表
     */
    List<SysRole> selectPage(@Param("query") RoleQueryDTO query);

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色
     */
    SysRole selectById(@Param("id") Long id);

    /**
     * 根据编码查询角色（含已删除，用于唯一性校验）
     *
     * @param code 角色编码
     * @return 角色
     */
    SysRole selectByCode(@Param("code") String code);

    /**
     * 根据名称查询角色（用于唯一性校验）
     *
     * @param name 角色名称
     * @return 角色
     */
    SysRole selectByName(@Param("name") String name);

    /**
     * 新增角色
     *
     * @param role 角色
     * @return 影响行数
     */
    int insert(SysRole role);

    /**
     * 更新角色
     *
     * @param role 角色
     * @return 影响行数
     */
    int updateById(SysRole role);

    /**
     * 逻辑删除角色
     *
     * @param id 角色ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 状态（1=启用 0=禁用）
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
