package com.yub.system.controller.log;

import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.common.model.Response;
import com.yub.system.dto.log.AccessLogQueryDTO;
import com.yub.system.service.log.SysAccessLogService;
import com.yub.system.vo.log.AccessLogPageRespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 访问日志管理 Controller
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 访问日志管理接口
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/system/access-log")
@RequiredArgsConstructor
public class SysAccessLogController {

    private final SysAccessLogService sysAccessLogService;

    /**
     * 分页查询访问日志
     *
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    @PostMapping("/page")
    @PreAuthorize("isAuthenticated()")
    public Response<PageResult<AccessLogPageRespVO>> page(@RequestBody PageQuery<AccessLogQueryDTO> pageQuery) {
        return Response.success(sysAccessLogService.page(pageQuery));
    }

    /**
     * 清理指定日期之前的访问日志
     *
     * @param beforeTime 清理该日期之前的日志（ISO 格式，如 2026-01-01T00:00:00）
     * @return 删除条数
     */
    @DeleteMapping("/clean")
    @PreAuthorize("isAuthenticated()")
    public Response<Integer> clean(@RequestParam LocalDateTime beforeTime) {
        int count = sysAccessLogService.clean(beforeTime);
        return Response.success(count);
    }
}
