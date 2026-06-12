package com.yub.system.mapper.user;

import com.yub.system.dto.user.UserQueryDTO;
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
     * 根据账号查询用户（含逻辑删除）
     *
     * @param account 账号
     * @return 用户实体，无则null
     */
    SysUser selectByAccountIncludeDeleted(@Param("account") String account);

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

    /**
     * 原子更新用户登录次数（避免并发丢失更新）
     *
     * @param id          用户ID
     * @param lastLoginIp 最后登录IP
     * @return 影响行数
     */
    int updateLoginCountAtomic(@Param("id") Long id, @Param("lastLoginIp") String lastLoginIp);

    /**
     * 分页查询用户列表（PageHelper 自动分页）
     *
     * @param query 查询条件
     * @return 用户列表
     */
    List<SysUser> select(@Param("query") UserQueryDTO query);

    /**
     * 新增用户
     *
     * @param user 用户实体
     */
    void insert(SysUser user);

    /**
     * 更新用户（选择性更新）
     *
     * @param user 用户实体
     */
    void updateById(SysUser user);

    /**
     * 逻辑删除用户
     *
     * @param id 用户ID
     */
    void deleteById(@Param("id") Long id);

    /**
     * 更新用户状态
     *
     * @param id     用户ID
     * @param status 状态（1启用 0禁用）
     */
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新用户密码
     *
     * @param id       用户ID
     * @param password BCrypt加密密码
     */
    void updatePassword(@Param("id") Long id, @Param("password") String password);

}
