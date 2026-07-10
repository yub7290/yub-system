package com.yub.system.mapper.dashboard;

import com.yub.system.vo.DashboardStatsRespVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 仪表盘数据访问层
 *
 * @Author: bing.yu
 * @CreateTime: 2026-07-09
 * @Description: 仪表盘统计查询
 * @Version: 1.0.0
 */
@Mapper
public interface DashboardMapper {

    /**
     * 统计课程总数
     *
     * @return 课程数
     */
    long countCourses();

    /**
     * 统计学员总数
     *
     * @return 学员数
     */
    long countStudents();

    /**
     * 统计教师总数
     *
     * @return 教师数
     */
    long countTeachers();

    /**
     * 统计试题总数
     *
     * @return 试题数
     */
    long countQuestions();

    /**
     * 统计今日考试数
     *
     * @return 考试数
     */
    long countTodayExams();

    /**
     * 统计今日新增学员数
     *
     * @return 学员数
     */
    long countTodayNewStudents();

    /**
     * 统计待处理订单数
     *
     * @return 订单数
     */
    long countPendingOrders();

    /**
     * 统计待批改作业数
     *
     * @return 作业数
     */
    long countPendingHomework();

    /**
     * 查询学员增长趋势（近30天）
     *
     * @return 趋势列表
     */
    List<DashboardStatsRespVO.TrendItem> selectStudentGrowth();

    /**
     * 查询订单与收入趋势（近30天）
     *
     * @return 趋势列表
     */
    List<DashboardStatsRespVO.OrderTrendItem> selectOrderAndIncome();

    /**
     * 查询考试通过率（本月）
     *
     * @return 通过率统计
     */
    DashboardStatsRespVO.ExamPassRate selectExamPassRate();

    /**
     * 查询课程热度排行 Top5
     *
     * @return 排行列表
     */
    List<DashboardStatsRespVO.CourseRankItem> selectTopCourses();

    /**
     * 查询最近登录记录
     *
     * @return 登录记录列表
     */
    List<DashboardStatsRespVO.LoginRecord> selectRecentLogins();

    /**
     * 查询最近操作动态
     *
     * @return 操作记录列表
     */
    List<DashboardStatsRespVO.OperationRecord> selectRecentOperations();
}
