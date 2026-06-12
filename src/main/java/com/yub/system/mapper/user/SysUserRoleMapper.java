package com.yub.system.mapper.user;

import com.yub.system.entity.user.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关联 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 用户角色关联数据访问层
 * @Version: 1.0.0
 */
@Mapper
public interface SysUserRoleMapper {

    /**
     * 批量插入用户角色关联
     *
     * @param list 用户角色关联列表
     */
    void insertBatch(@Param("list") List<SysUserRole> list);

    /**
     * 根据用户ID删除所有角色关联
     *
     * @param userId 用户ID
     */
    void deleteByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);
}
