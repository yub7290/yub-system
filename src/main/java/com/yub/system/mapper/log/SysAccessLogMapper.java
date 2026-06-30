package com.yub.system.mapper.log;

import com.yub.system.dto.log.AccessLogQueryDTO;
import com.yub.system.entity.log.SysAccessLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 访问日志 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 访问日志数据访问层
 * @Version: 1.0.0
 */
@Mapper
public interface SysAccessLogMapper {

    /**
     * 插入单条访问日志
     *
     * @param log 日志实体
     * @return 影响行数
     */
    int insert(SysAccessLog log);

    /**
     * 批量插入访问日志
     *
     * @param logs 日志实体列表
     * @return 影响行数
     */
    int batchInsert(List<SysAccessLog> logs);

    /**
     * 分页查询访问日志
     *
     * @param query 查询条件
     * @return 日志列表
     */
    List<SysAccessLog> selectPage(@Param("query") AccessLogQueryDTO query);

    /**
     * 按创建时间删除访问日志（用于数据清理）
     *
     * @param beforeTime 删除该时间之前的日志
     * @return 影响行数
     */
    int deleteByCreateTime(@Param("beforeTime") LocalDateTime beforeTime);
}
