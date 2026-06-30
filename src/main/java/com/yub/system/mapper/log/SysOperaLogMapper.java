package com.yub.system.mapper.log;

import com.yub.system.entity.log.SysOperaLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统操作日志 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 系统操作日志数据访问层
 * @Version: 1.0.0
 */
@Mapper
public interface SysOperaLogMapper {

    /**
     * 插入单条操作日志
     *
     * @param log 操作日志实体
     * @return 影响行数
     */
    int insert(SysOperaLog log);

    /**
     * 批量插入操作日志
     *
     * @param logs 操作日志实体列表
     * @return 影响行数
     */
    int batchInsert(List<SysOperaLog> logs);

    /**
     * 分页查询操作日志
     *
     * @param query 查询条件
     * @return 日志列表
     */
    List<SysOperaLog> selectPage(@Param("query") com.yub.system.dto.log.OperaLogQueryDTO query);

    /**
     * 查询操作日志详情
     *
     * @param id 日志ID
     * @return 操作日志
     */
    SysOperaLog selectById(@Param("id") Long id);

    /**
     * 按创建时间删除操作日志（用于数据清理）
     *
     * @param beforeTime 删除该时间之前的日志
     * @return 影响行数
     */
    int deleteByCreateTime(@Param("beforeTime") LocalDateTime beforeTime);
}
