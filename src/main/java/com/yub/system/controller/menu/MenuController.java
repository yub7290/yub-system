package com.yub.system.controller.menu;

import com.yub.common.model.Response;
import com.yub.system.dto.menu.MenuCreateReqDTO;
import com.yub.system.dto.menu.MenuUpdateReqDTO;
import com.yub.system.dto.user.StatusReqDTO;
import com.yub.system.entity.menu.SysMenu;
import com.yub.system.service.menu.SysMenuService;
import com.yub.system.vo.menu.MenuDetailRespVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单管理 Controller
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 菜单管理接口
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class MenuController {

    private final SysMenuService sysMenuService;

    /**
     * 获取菜单树
     *
     * @param name   菜单名称（可选，模糊搜索）
     * @param status 状态（可选）
     * @return 菜单树
     */
    @GetMapping("/tree")
    public Response<List<SysMenu>> tree(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) Integer status) {
        if (name != null && !name.isEmpty()) {
            return Response.success(sysMenuService.selectTreeByCondition(name, status));
        }
        return Response.success(sysMenuService.selectTree());
    }

    /**
     * 获取菜单详情
     *
     * @param id 菜单ID
     * @return 菜单详情
     */
    @GetMapping("/{id}")
    public Response<MenuDetailRespVO> getDetail(@PathVariable Long id) {
        return Response.success(sysMenuService.getDetail(id));
    }

    /**
     * 新增菜单
     *
     * @param dto 新增参数
     * @return 菜单ID
     */
    @PostMapping
    public Response<Long> create(@Valid @RequestBody MenuCreateReqDTO dto) {
        return Response.success(sysMenuService.create(dto));
    }

    /**
     * 编辑菜单
     *
     * @param dto 编辑参数
     * @return 响应
     */
    @PutMapping
    public Response<Void> update(@Valid @RequestBody MenuUpdateReqDTO dto) {
        sysMenuService.update(dto);
        return Response.success();
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return 响应
     */
    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        sysMenuService.delete(id);
        return Response.success();
    }

    /**
     * 切换菜单状态
     *
     * @param id  菜单ID
     * @param dto 状态参数
     * @return 响应
     */
    @PutMapping("/{id}/status")
    public Response<Void> changeStatus(@PathVariable Long id, @Valid @RequestBody StatusReqDTO dto) {
        sysMenuService.changeStatus(id, dto.getStatus());
        return Response.success();
    }
}
