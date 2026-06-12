package com.yub.system.service.user;

import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.system.dto.user.UserCreateReqDTO;
import com.yub.system.dto.user.UserQueryDTO;
import com.yub.system.dto.user.UserUpdateReqDTO;
import com.yub.system.vo.user.UserDetailRespVO;
import com.yub.system.vo.user.UserPageRespVO;

/**
 * 用户管理 Service 接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 用户管理业务接口
 * @Version: 1.0.0
 */
public interface UserService {

    /**
     * 分页查询用户列表
     *
     * @param pageQuery@return 分页结果
     */
    PageResult<UserPageRespVO> page(PageQuery<UserQueryDTO> pageQuery);

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    UserDetailRespVO getDetail(Long id);

    /**
     * 新增用户
     *
     * @param req 新增请求
     */
    void create(UserCreateReqDTO req);

    /**
     * 编辑用户
     *
     * @param req 编辑请求
     */
    void update(UserUpdateReqDTO req);

    /**
     * 删除用户（逻辑删除）
     *
     * @param id 用户ID
     */
    void delete(Long id);

    /**
     * 重置密码
     *
     * @param id 用户ID
     */
    void resetPassword(Long id);

    /**
     * 启用/禁用用户
     *
     * @param id     用户ID
     * @param status 状态（1启用 0禁用）
     */
    void changeStatus(Long id, Integer status);
}
