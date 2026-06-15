package com.yub.system.service.dept;

import com.yub.system.dto.dept.DeptCreateReqDTO;
import com.yub.system.dto.dept.DeptQueryDTO;
import com.yub.system.dto.dept.DeptUpdateReqDTO;
import com.yub.system.entity.dept.SysDept;
import com.yub.system.vo.dept.DeptDetailRespVO;

import java.util.List;

/**
 * 部门服务接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 部门管理服务
 * @Version: 1.0.0
 */
public interface SysDeptService {

    /**
     * 获取部门树
     *
     * @return 部门树列表
     */
    List<SysDept> selectTree();

    /**
     * 获取部门树（按条件）
     *
     * @param query 查询条件
     * @return 部门树列表
     */
    List<SysDept> selectTreeByCondition(DeptQueryDTO query);

    /**
     * 获取部门详情
     *
     * @param id 部门ID
     * @return 部门详情
     */
    DeptDetailRespVO getDetail(Long id);

    /**
     * 新增部门
     *
     * @param dto 新增参数
     * @return 部门ID
     */
    Long create(DeptCreateReqDTO dto);

    /**
     * 编辑部门
     *
     * @param dto 编辑参数
     */
    void update(DeptUpdateReqDTO dto);

    /**
     * 删除部门
     *
     * @param id 部门ID
     */
    void delete(Long id);

    /**
     * 切换部门状态
     *
     * @param id     部门ID
     * @param status 状态（1=启用 0=禁用）
     */
    void changeStatus(Long id, Integer status);
}
