package com.yub.system.controller.user;

import com.yub.common.annotation.Log;
import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.common.model.Response;
import com.yub.system.dto.user.StatusReqDTO;
import com.yub.system.dto.user.UserCreateReqDTO;
import com.yub.system.dto.user.UserQueryDTO;
import com.yub.system.dto.user.UserUpdateReqDTO;
import com.yub.system.service.user.UserService;
import com.yub.system.vo.user.UserDetailRespVO;
import com.yub.system.vo.user.UserPageRespVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 用户管理接口
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 分页查询用户列表
     *
     * @param query 查询条件+分页参数（JSON body）
     * @return 分页结果
     */
    @PostMapping("/page")
    public Response<PageResult<UserPageRespVO>> page(@Valid @RequestBody PageQuery<UserQueryDTO> query) {
        return Response.success(userService.page(query));
    }

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @GetMapping("/{id}")
    public Response<UserDetailRespVO> get(@PathVariable("id") Long id) {
        return Response.success(userService.getDetail(id));
    }

    /**
     * 新增用户
     *
     * @param req 新增请求
     * @return 操作结果
     */
    @PostMapping
    @Log(value = "新增用户", type = "CREATE")
    public Response<Void> create(@Valid @RequestBody UserCreateReqDTO req) {
        userService.create(req);
        return Response.success();
    }

    /**
     * 编辑用户
     *
     * @param req 编辑请求
     * @return 操作结果
     */
    @PutMapping
    @Log(value = "编辑用户", type = "UPDATE")
    public Response<Void> update(@Valid @RequestBody UserUpdateReqDTO req) {
        userService.update(req);
        return Response.success();
    }

    /**
     * 删除用户（逻辑删除）
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Log(value = "删除用户", type = "DELETE")
    public Response<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return Response.success();
    }

    /**
     * 重置密码
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @PutMapping("/{id}/password")
    @Log(value = "重置用户密码", type = "UPDATE")
    public Response<Void> resetPassword(@PathVariable("id") Long id) {
        userService.resetPassword(id);
        return Response.success();
    }

    /**
     * 启用/禁用用户
     *
     * @param id  用户ID
     * @param req 请求体（包含status字段）
     * @return 操作结果
     */
    @PutMapping("/{id}/status")
    @Log(value = "启用/禁用用户", type = "UPDATE")
    public Response<Void> changeStatus(@PathVariable("id") Long id, @Valid @RequestBody StatusReqDTO req) {
        userService.changeStatus(id, req.getStatus());
        return Response.success();
    }

}
