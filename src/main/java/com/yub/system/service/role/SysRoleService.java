package com.yub.system.service.role;

import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.system.dto.role.RoleCreateReqDTO;
import com.yub.system.dto.role.RoleQueryDTO;
import com.yub.system.dto.role.RoleUpdateReqDTO;
import com.yub.system.entity.role.SysRole;
import com.yub.system.vo.role.RoleDetailRespVO;
import com.yub.system.vo.role.RolePageRespVO;

import java.util.List;

/**
 * 角色服务接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 角色管理服务
 * @Version: 1.0.0
 */
public interface SysRoleService {

    /**
     * 查询所有启用角色
     *
     * @return 角色列表
     */
    List<SysRole> selectAllEnabled();

    /**
     * 分页查询角色
     *
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    PageResult<RolePageRespVO> page(PageQuery<RoleQueryDTO> pageQuery);

    /**
     * 获取角色详情
     *
     * @param id 角色ID
     * @return 角色详情
     */
    RoleDetailRespVO getDetail(Long id);

    /**
     * 新增角色
     *
     * @param dto 新增参数
     * @return 角色ID
     */
    Long create(RoleCreateReqDTO dto);

    /**
     * 编辑角色
     *
     * @param dto 编辑参数
     */
    void update(RoleUpdateReqDTO dto);

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    void delete(Long id);

    /**
     * 切换角色状态
     *
     * @param id     角色ID
     * @param status 状态（1=启用 0=禁用）
     */
    void changeStatus(Long id, Integer status);
}
