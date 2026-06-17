package com.yub.system.controller.dict;

import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.common.model.Response;
import com.yub.system.dto.dict.DictTypeCreateReqDTO;
import com.yub.system.dto.dict.DictTypeQueryDTO;
import com.yub.system.dto.dict.DictTypeUpdateReqDTO;
import com.yub.system.service.dict.SysDictTypeService;
import com.yub.system.vo.dict.DictTypeDetailRespVO;
import com.yub.system.vo.dict.DictTypePageRespVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.yub.system.dto.user.StatusReqDTO;

/**
 * 字典类型 Controller
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@RestController
@RequestMapping("/system/dict/type")
@RequiredArgsConstructor
public class DictTypeController {

    private final SysDictTypeService sysDictTypeService;

    @PostMapping("/page")
    public Response<PageResult<DictTypePageRespVO>> page(@RequestBody PageQuery<DictTypeQueryDTO> pageQuery) {
        return Response.success(sysDictTypeService.page(pageQuery));
    }

    @GetMapping("/{id}")
    public Response<DictTypeDetailRespVO> getDetail(@PathVariable Long id) {
        return Response.success(sysDictTypeService.getDetail(id));
    }

    @PostMapping
    public Response<Long> create(@Valid @RequestBody DictTypeCreateReqDTO dto) {
        return Response.success(sysDictTypeService.create(dto));
    }

    @PutMapping
    public Response<Void> update(@Valid @RequestBody DictTypeUpdateReqDTO dto) {
        sysDictTypeService.update(dto);
        return Response.success();
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        sysDictTypeService.delete(id);
        return Response.success();
    }

    @PutMapping("/{id}/status")
    public Response<Void> changeStatus(@PathVariable Long id, @Valid @RequestBody StatusReqDTO dto) {
        sysDictTypeService.changeStatus(id, dto.getStatus());
        return Response.success();
    }
}
