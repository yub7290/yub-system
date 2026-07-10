package com.yub.system.controller.dict;

import com.yub.common.annotation.Log;
import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.common.model.Response;
import com.yub.system.dto.dict.DictDataCreateReqDTO;
import com.yub.system.dto.dict.DictDataQueryDTO;
import com.yub.system.dto.dict.DictDataUpdateReqDTO;
import com.yub.system.service.dict.SysDictDataService;
import com.yub.system.vo.dict.DictDataOptionVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据 Controller
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-15
 */
@RestController
@RequestMapping("/system/dict/data")
@RequiredArgsConstructor
public class DictDataController {

    private final SysDictDataService sysDictDataService;

    /**
     * 分页查询字典数据
     */
    @PostMapping("/page")
    public Response<?> page(@RequestBody PageQuery<DictDataQueryDTO> pageQuery) {
        return Response.success(sysDictDataService.page(pageQuery));
    }

    /**
     * 获取字典数据详情
     */
    @GetMapping("/{id}")
    public Response<?> getDetail(@PathVariable("id") Long id) {
        return Response.success(sysDictDataService.getDetail(id));
    }

    /**
     * 新增字典数据
     */
    @PostMapping
    @Log(value = "新增字典数据", type = "CREATE")
    public Response<Long> create(@Valid @RequestBody DictDataCreateReqDTO dto) {
        return Response.success(sysDictDataService.create(dto));
    }

    /**
     * 编辑字典数据
     */
    @PutMapping
    @Log(value = "编辑字典数据", type = "UPDATE")
    public Response<Void> update(@Valid @RequestBody DictDataUpdateReqDTO dto) {
        sysDictDataService.update(dto);
        return Response.success();
    }

    /**
     * 删除字典数据
     */
    @DeleteMapping("/{id}")
    @Log(value = "删除字典数据", type = "DELETE")
    public Response<Void> delete(@PathVariable("id") Long id) {
        sysDictDataService.delete(id);
        return Response.success();
    }

    /**
     * 根据字典类型编码获取字典选项列表
     */
    @GetMapping("/options/{code}")
    public Response<List<DictDataOptionVO>> getOptions(@PathVariable("code") String code) {
        return Response.success(sysDictDataService.getOptionsByCode(code));
    }
}
