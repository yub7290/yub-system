package com.yub.system.mapper.user;

import com.yub.system.entity.user.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 用户数据访问层
 * @Version: 1.0.0
 */
@Mapper
public interface SysUserMapper {

    SysUser selectByAccount(@Param("account") String account);

    SysUser selectById(@Param("id") Long id);

    List<String> selectRoleCodesByUserId(@Param("userId") Long userId);
}
