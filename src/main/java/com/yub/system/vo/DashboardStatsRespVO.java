package com.yub.system.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 仪表盘统计响应 VO
 *
 * @Author: bing.yu
 * @CreateTime: 2026-07-09
 * @Description: 仪表盘全量统计数据
 * @Version: 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsRespVO {

    /** 课程总数 */
    private long courseCount;
    /** 学员总数 */
    private long studentCount;
    /** 教师总数 */
    private long teacherCount;
    /** 试题总数 */
    private long questionCount;
    /** 今日考试数 */
    private long todayExamCount;
    /** 今日新增学员数 */
    private long todayNewStudents;
    /** 待处理订单数 */
    private long pendingOrderCount;
    /** 待批改作业数 */
    private long pendingHomeworkCount;

    /** 学员增长趋势（近30天） */
    private List<TrendItem> studentGrowth;
    /** 订单与收入趋势（近30天） */
    private List<OrderTrendItem> orderAndIncome;
    /** 考试通过率 */
    private ExamPassRate examPassRate;

    /** 课程热度排行 Top5 */
    private List<CourseRankItem> topCourses;
    /** 最近登录记录 */
    private List<LoginRecord> recentLogins;
    /** 最近操作动态 */
    private List<OperationRecord> recentOperations;

    /**
     * 趋势数据项
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrendItem {
        /** 日期 */
        private String date;
        /** 数量 */
        private long count;
    }

    /**
     * 订单与收入趋势数据项
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderTrendItem {
        /** 日期 */
        private String date;
        /** 订单数 */
        private long orderCount;
        /** 收入金额 */
        private BigDecimal income;
    }

    /**
     * 考试通过率
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExamPassRate {
        /** 通过数 */
        private long passed;
        /** 未通过数 */
        private long failed;
        /** 待批改数 */
        private long pending;
    }

    /**
     * 课程热度排行项
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseRankItem {
        /** 课程ID */
        private Long id;
        /** 课程名称 */
        private String name;
        /** 学员数 */
        private long studentCount;
    }

    /**
     * 登录记录
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRecord {
        /** 学员姓名 */
        private String studentName;
        /** 登录时间 */
        private String loginTime;
        /** IP地址 */
        private String ip;
    }

    /**
     * 操作动态
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperationRecord {
        /** 操作人 */
        private String operator;
        /** 操作内容 */
        private String content;
        /** 操作时间 */
        private String time;
    }
}
