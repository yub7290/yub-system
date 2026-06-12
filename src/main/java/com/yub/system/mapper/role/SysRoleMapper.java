package com.yub.system.mapper.role;

import com.yub.system.entity.role.SysRole;
import org.apache.ibatis.annotations.Mapper;

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
     * 查询所有启用角色
     *
     * @return 角色列表
     */
    List<SysRole> selectAllEnabled();
}
