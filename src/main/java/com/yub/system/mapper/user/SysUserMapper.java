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

    /**
     * 根据账号查询用户
     *
     * @param account 账号
     * @return 用户实体，无则null
     */
    SysUser selectByAccount(@Param("account") String account);

    /**
     * 根据用户ID查询用户
     *
     * @param id 用户ID
     * @return 用户实体，无则null
     */
    SysUser selectById(@Param("id") Long id);

    /**
     * 查询用户角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    List<String> selectRoleCodesByUserId(@Param("userId") Long userId);

    /**
     * 更新用户登录信息（最后登录时间、IP、登录次数）
     *
     * @param user 用户实体（携带最新登录信息）
     * @return 影响行数
     */
    int updateLoginInfo(SysUser user);
}
