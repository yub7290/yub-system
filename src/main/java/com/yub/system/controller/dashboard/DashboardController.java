package com.yub.system.controller.dashboard;

import com.yub.common.model.Response;
import com.yub.system.mapper.dashboard.DashboardMapper;
import com.yub.system.vo.DashboardStatsRespVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * 仪表盘 Controller
 *
 * @Author: bing.yu
 * @CreateTime: 2026-07-09
 * @Description: 仪表盘统计接口
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardMapper dashboardMapper;

    /**
     * 获取仪表盘全量统计数据
     *
     * @return 仪表盘统计数据
     */
    @GetMapping("/stats")
    public Response<DashboardStatsRespVO> stats() {
        log.info("查询仪表盘统计数据");
        DashboardStatsRespVO vo = buildDashboardStats();
        return Response.success(vo);
    }

    /**
     * 组装仪表盘全量统计数据
     *
     * @return 仪表盘统计 VO
     */
    private DashboardStatsRespVO buildDashboardStats() {
        DashboardStatsRespVO vo = DashboardStatsRespVO.builder()
                .courseCount(safeCall(dashboardMapper::countCourses, 0L))
                .studentCount(safeCall(dashboardMapper::countStudents, 0L))
                .teacherCount(safeCall(dashboardMapper::countTeachers, 0L))
                .questionCount(safeCall(dashboardMapper::countQuestions, 0L))
                .todayExamCount(safeCall(dashboardMapper::countTodayExams, 0L))
                .todayNewStudents(safeCall(dashboardMapper::countTodayNewStudents, 0L))
                .pendingOrderCount(safeCall(dashboardMapper::countPendingOrders, 0L))
                .pendingHomeworkCount(safeCall(dashboardMapper::countPendingHomework, 0L))
                .build();

        vo.setStudentGrowth(safeList(safeCall(dashboardMapper::selectStudentGrowth, null)));
        vo.setOrderAndIncome(safeList(safeCall(dashboardMapper::selectOrderAndIncome, null)));
        vo.setExamPassRate(safeCall(dashboardMapper::selectExamPassRate,
                DashboardStatsRespVO.ExamPassRate.builder().build()));
        vo.setTopCourses(safeList(safeCall(dashboardMapper::selectTopCourses, null)));
        vo.setRecentLogins(safeList(safeCall(dashboardMapper::selectRecentLogins, null)));
        vo.setRecentOperations(safeList(safeCall(dashboardMapper::selectRecentOperations, null)));

        return vo;
    }

    /**
     * 安全调用，异常时返回默认值
     *
     * @param supplier     调用逻辑
     * @param defaultValue 默认值
     * @return 结果或默认值
     */
    private <T> T safeCall(java.util.function.Supplier<T> supplier, T defaultValue) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.warn("仪表盘查询异常: {}", e.getMessage());
            return defaultValue;
        }
    }

    /**
     * 安全获取列表，null 转为空列表
     *
     * @param list 原始列表
     * @return 非空列表
     */
    private <T> java.util.List<T> safeList(java.util.List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }
}
