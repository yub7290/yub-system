package com.yub.system.controller.banner;

import com.yub.common.annotation.Log;
import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.common.model.Response;
import com.yub.system.dto.banner.BannerCreateReqDTO;
import com.yub.system.dto.banner.BannerPageReqDTO;
import com.yub.system.dto.banner.BannerUpdateReqDTO;
import com.yub.system.service.banner.SysBannerService;
import com.yub.system.vo.banner.BannerDetailRespVO;
import com.yub.system.vo.banner.BannerPageRespVO;
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

/**
 * Banner管理 Controller
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-22
 * @Description: Banner管理接口
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/system/banner")
@RequiredArgsConstructor
public class BannerController {

    private final SysBannerService sysBannerService;

    /**
     * 分页查询Banner列表
     *
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    @PostMapping("/page")
    @PreAuthorize("isAuthenticated()")
    public Response<PageResult<BannerPageRespVO>> page(@RequestBody PageQuery<BannerPageReqDTO> pageQuery) {
        return Response.success(sysBannerService.page(pageQuery));
    }

    /**
     * 获取Banner详情
     *
     * @param id Banner ID
     * @return Banner详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Response<BannerDetailRespVO> getDetail(@PathVariable("id") Long id) {
        return Response.success(sysBannerService.getDetail(id));
    }

    /**
     * 新增Banner
     *
     * @param dto 新增参数
     * @return Banner ID
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Log(value = "新增Banner", type = "CREATE")
    public Response<Long> create(@Valid @RequestBody BannerCreateReqDTO dto) {
        return Response.success(sysBannerService.create(dto));
    }

    /**
     * 编辑Banner
     *
     * @param dto 编辑参数
     * @return 响应
     */
    @PutMapping
    @PreAuthorize("isAuthenticated()")
    @Log(value = "编辑Banner", type = "UPDATE")
    public Response<Void> update(@Valid @RequestBody BannerUpdateReqDTO dto) {
        sysBannerService.update(dto);
        return Response.success();
    }

    /**
     * 删除Banner
     *
     * @param id Banner ID
     * @return 响应
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Log(value = "删除Banner", type = "DELETE")
    public Response<Void> delete(@PathVariable("id") Long id) {
        sysBannerService.delete(id);
        return Response.success();
    }
}
