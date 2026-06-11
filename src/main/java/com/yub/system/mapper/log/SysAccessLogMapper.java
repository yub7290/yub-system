package com.yub.system.mapper.log;

import com.yub.system.entity.log.SysAccessLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志 Mapper
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-10
 * @Description: 登录日志数据访问层
 * @Version: 1.0.0
 */
@Mapper
public interface SysAccessLogMapper {

    /**
     * 插入登录日志
     *
     * @param log 日志实体
     * @return 影响行数
     */
    int insert(SysAccessLog log);
}
