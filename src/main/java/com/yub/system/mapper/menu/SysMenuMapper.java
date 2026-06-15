package com.yub.system.mapper.menu;

import com.yub.system.dto.menu.MenuQueryDTO;
import com.yub.system.entity.menu.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 菜单数据访问层
 * @Version: 1.0.0
 */
@Mapper
public interface SysMenuMapper {

    /**
     * 根据用户ID查询菜单列表
     *
     * @param userId 用户ID
     * @return 用户拥有的菜单列表
     */
    List<SysMenu> selectByUserId(@Param("userId") Long userId);

    /**
     * 查询所有启用的菜单（超级管理员用）
     *
     * @return 所有菜单列表
     */
    List<SysMenu> selectAll();

    /**
     * 查询所有菜单（管理端用，忽略状态过滤）
     *
     * @return 所有菜单列表
     */
    List<SysMenu> selectManagementList();

    /**
     * 查询菜单列表（按条件）
     *
     * @param query 查询参数
     * @return 菜单列表
     */
    List<SysMenu> selectList(@Param("query") MenuQueryDTO query);

    /**
     * 根据ID查询菜单
     *
     * @param id 菜单ID
     * @return 菜单
     */
    SysMenu selectById(@Param("id") Long id);

    /**
     * 根据名称和父ID查询菜单（唯一性校验）
     *
     * @param name     菜单名称
     * @param parentId 父菜单ID
     * @return 菜单
     */
    SysMenu selectByNameAndParentId(@Param("name") String name, @Param("parentId") Long parentId);

    /**
     * 查询子菜单数量
     *
     * @param parentId 父菜单ID
     * @return 子菜单数量
     */
    int countByParentId(@Param("parentId") Long parentId);

    /**
     * 根据ID列表查询菜单
     *
     * @param ids 菜单ID列表
     * @return 菜单列表
     */
    List<SysMenu> selectByIds(@Param("ids") List<Long> ids);

    /**
     * 新增菜单
     *
     * @param menu 菜单
     * @return 影响行数
     */
    int insert(SysMenu menu);

    /**
     * 更新菜单
     *
     * @param menu 菜单
     * @return 影响行数
     */
    int updateById(SysMenu menu);

    /**
     * 逻辑删除菜单
     *
     * @param id 菜单ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 更新菜单状态
     *
     * @param id     菜单ID
     * @param status 状态（1=启用 0=禁用）
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
