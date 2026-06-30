package com.yub.system.controller.role;

import com.yub.common.annotation.Log;
import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.common.model.Response;
import com.yub.system.dto.role.RoleCreateReqDTO;
import com.yub.system.dto.role.RoleQueryDTO;
import com.yub.system.dto.role.RoleUpdateReqDTO;
import com.yub.system.dto.user.StatusReqDTO;
import com.yub.system.entity.role.SysRole;
import com.yub.system.service.role.SysRoleService;
import com.yub.system.vo.role.RoleDetailRespVO;
import com.yub.system.vo.role.RoleOptionRespVO;
import com.yub.system.vo.role.RolePageRespVO;
import com.yub.framework.util.BeanUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理 Controller
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-11
 * @Description: 角色管理接口
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class RoleController {

    private final SysRoleService sysRoleService;

    /**
     * 获取角色选项列表（下拉框用）
     *
     * @return 角色列表
     */
    @GetMapping("/options")
    @PreAuthorize("isAuthenticated()")
    public Response<List<RoleOptionRespVO>> options() {
        List<SysRole> roles = sysRoleService.selectAllEnabled();
        return Response.success(BeanUtils.copyList(roles, RoleOptionRespVO.class));
    }

    /**
     * 分页查询角色
     *
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    @PostMapping("/page")
    @PreAuthorize("isAuthenticated()")
    public Response<PageResult<RolePageRespVO>> page(@RequestBody PageQuery<RoleQueryDTO> pageQuery) {
        return Response.success(sysRoleService.page(pageQuery));
    }

    /**
     * 获取角色详情
     *
     * @param id 角色ID
     * @return 角色详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Response<RoleDetailRespVO> getDetail(@PathVariable Long id) {
        return Response.success(sysRoleService.getDetail(id));
    }

    /**
     * 新增角色
     *
     * @param dto 新增参数
     * @return 角色ID
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Log(value = "新增角色", type = "CREATE")
    public Response<Long> create(@Valid @RequestBody RoleCreateReqDTO dto) {
        return Response.success(sysRoleService.create(dto));
    }

    /**
     * 编辑角色
     *
     * @param dto 编辑参数
     * @return 响应
     */
    @PutMapping
    @PreAuthorize("isAuthenticated()")
    @Log(value = "编辑角色", type = "UPDATE")
    public Response<Void> update(@Valid @RequestBody RoleUpdateReqDTO dto) {
        sysRoleService.update(dto);
        return Response.success();
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 响应
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Log(value = "删除角色", type = "DELETE")
    public Response<Void> delete(@PathVariable Long id) {
        sysRoleService.delete(id);
        return Response.success();
    }

    /**
     * 切换角色状态
     *
     * @param id   角色ID
     * @param dto  状态参数
     * @return 响应
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("isAuthenticated()")
    @Log(value = "切换角色状态", type = "UPDATE")
    public Response<Void> changeStatus(@PathVariable Long id, @Valid @RequestBody StatusReqDTO dto) {
        sysRoleService.changeStatus(id, dto.getStatus());
        return Response.success();
    }
}
