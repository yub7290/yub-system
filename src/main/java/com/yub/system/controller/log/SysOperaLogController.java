package com.yub.system.controller.log;

import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.common.model.Response;
import com.yub.system.dto.log.OperaLogQueryDTO;
import com.yub.system.service.log.SysOperaLogService;
import com.yub.system.vo.log.OperaLogDetailRespVO;
import com.yub.system.vo.log.OperaLogPageRespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 操作日志管理 Controller
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 操作日志管理接口
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/system/opera-log")
@RequiredArgsConstructor
public class SysOperaLogController {

    private final SysOperaLogService sysOperaLogService;

    /**
     * 分页查询操作日志
     *
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    @PostMapping("/page")
    @PreAuthorize("isAuthenticated()")
    public Response<PageResult<OperaLogPageRespVO>> page(@RequestBody PageQuery<OperaLogQueryDTO> pageQuery) {
        return Response.success(sysOperaLogService.page(pageQuery));
    }

    /**
     * 获取操作日志详情（含请求参数和响应结果）
     *
     * @param id 日志ID
     * @return 日志详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Response<OperaLogDetailRespVO> getDetail(@PathVariable("id") Long id) {
        return Response.success(sysOperaLogService.getDetail(id));
    }

    /**
     * 清理指定日期之前的操作日志
     *
     * @param beforeTime 清理该日期之前的日志（ISO 格式）
     * @return 删除条数
     */
    @DeleteMapping("/clean")
    @PreAuthorize("isAuthenticated()")
    public Response<Integer> clean(@RequestParam LocalDateTime beforeTime) {
        int count = sysOperaLogService.clean(beforeTime);
        return Response.success(count);
    }
}
