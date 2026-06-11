package com.yub.system.mapper.menu;

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
}
