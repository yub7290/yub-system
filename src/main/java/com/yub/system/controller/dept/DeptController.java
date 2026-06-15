package com.yub.system.controller.dept;

import com.yub.common.model.Response;
import com.yub.system.dto.dept.DeptCreateReqDTO;
import com.yub.system.dto.dept.DeptQueryDTO;
import com.yub.system.dto.dept.DeptUpdateReqDTO;
import com.yub.system.dto.user.StatusReqDTO;
import com.yub.system.entity.dept.SysDept;
import com.yub.system.service.dept.SysDeptService;
import com.yub.system.vo.dept.DeptDetailRespVO;
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
 * 部门管理 Controller
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 * @Description: 部门管理接口
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/system/dept")
@RequiredArgsConstructor
public class DeptController {

    private final SysDeptService sysDeptService;

    /**
     * 获取部门树
     *
     * @param deptName 部门名称（可选，模糊搜索）
     * @param status   状态（可选）
     * @return 部门树
     */
    @GetMapping("/tree")
    public Response<List<SysDept>> tree(@RequestParam(required = false) String deptName,
                                        @RequestParam(required = false) Integer status) {
        if ((deptName != null && !deptName.isEmpty()) || status != null) {
            DeptQueryDTO query = new DeptQueryDTO();
            query.setDeptName(deptName);
            query.setStatus(status);
            return Response.success(sysDeptService.selectTreeByCondition(query));
        }
        return Response.success(sysDeptService.selectTree());
    }

    /**
     * 获取部门详情
     *
     * @param id 部门ID
     * @return 部门详情
     */
    @GetMapping("/{id}")
    public Response<DeptDetailRespVO> getDetail(@PathVariable Long id) {
        return Response.success(sysDeptService.getDetail(id));
    }

    /**
     * 新增部门
     *
     * @param dto 新增参数
     * @return 部门ID
     */
    @PostMapping
    public Response<Long> create(@Valid @RequestBody DeptCreateReqDTO dto) {
        return Response.success(sysDeptService.create(dto));
    }

    /**
     * 编辑部门
     *
     * @param dto 编辑参数
     * @return 响应
     */
    @PutMapping
    public Response<Void> update(@Valid @RequestBody DeptUpdateReqDTO dto) {
        sysDeptService.update(dto);
        return Response.success();
    }

    /**
     * 删除部门
     *
     * @param id 部门ID
     * @return 响应
     */
    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        sysDeptService.delete(id);
        return Response.success();
    }

    /**
     * 切换部门状态
     *
     * @param id  部门ID
     * @param dto 状态参数
     * @return 响应
     */
    @PutMapping("/{id}/status")
    public Response<Void> changeStatus(@PathVariable Long id, @Valid @RequestBody StatusReqDTO dto) {
        sysDeptService.changeStatus(id, dto.getStatus());
        return Response.success();
    }
}
