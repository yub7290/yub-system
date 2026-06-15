package com.yub.system.service.menu;

import com.yub.system.entity.menu.SysMenu;
import com.yub.system.dto.menu.MenuCreateReqDTO;
import com.yub.system.dto.menu.MenuUpdateReqDTO;
import com.yub.system.vo.menu.MenuDetailRespVO;

import java.util.List;

/**
 * 菜单服务接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 菜单管理服务
 * @Version: 1.0.0
 */
public interface SysMenuService {

    /**
     * 获取菜单树
     *
     * @return 菜单树列表
     */
    List<SysMenu> selectTree();

    /**
     * 获取菜单列表（按条件）
     *
     * @param name   菜单名称（可选）
     * @param status 状态（可选）
     * @return 菜单树列表
     */
    List<SysMenu> selectTreeByCondition(String name, Integer status);

    /**
     * 获取菜单详情
     *
     * @param id 菜单ID
     * @return 菜单详情
     */
    MenuDetailRespVO getDetail(Long id);

    /**
     * 新增菜单
     *
     * @param dto 新增参数
     * @return 菜单ID
     */
    Long create(MenuCreateReqDTO dto);

    /**
     * 编辑菜单
     *
     * @param dto 编辑参数
     */
    void update(MenuUpdateReqDTO dto);

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     */
    void delete(Long id);

    /**
     * 切换菜单状态
     *
     * @param id     菜单ID
     * @param status 状态（1=启用 0=禁用）
     */
    void changeStatus(Long id, Integer status);
}
