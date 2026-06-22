package com.yub.system.controller.dashboard;

import com.yub.common.model.Response;
import com.yub.system.vo.DashboardStatsRespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 仪表盘 Controller
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-22
 * @Description: 仪表盘统计接口
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    /**
     * 获取仪表盘统计数据
     *
     * @return 统计卡片数据
     */
    @GetMapping("/stats")
    public Response<DashboardStatsRespVO> stats() {
        return Response.success(DashboardStatsRespVO.builder()
                .courseCount(0)
                .studentCount(0)
                .examCount(0)
                .accessCount(0)
                .build());
    }
}
