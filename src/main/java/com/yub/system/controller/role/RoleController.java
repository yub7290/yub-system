package com.yub.system.controller.role;

import com.yub.common.model.Response;
import com.yub.system.entity.role.SysRole;
import com.yub.system.service.role.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色查询 Controller
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-12
 * @Description: 角色查询接口（供下拉框使用）
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class RoleController {

    private final SysRoleService sysRoleService;

    /**
     * 获取所有启用角色
     *
     * @return 角色列表
     */
    @GetMapping("/options")
    public Response<List<SysRole>> options() {
        return Response.success(sysRoleService.selectAllEnabled());
    }
}
