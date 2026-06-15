package com.yub.system.mapper.role;

import com.yub.system.entity.menu.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色菜单关联 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 角色菜单关联数据访问层
 * @Version: 1.0.0
 */
@Mapper
public interface SysRoleMenuMapper {

    /**
     * 批量插入角色菜单关联
     *
     * @param list 关联列表
     * @return 影响行数
     */
    int insertBatch(@Param("list") List<SysRoleMenu> list);

    /**
     * 根据角色ID删除所有关联
     *
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 查询角色已分配的菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);
}
