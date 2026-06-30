package com.yub.system.service.log;

import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.system.dto.log.AccessLogQueryDTO;
import com.yub.system.vo.log.AccessLogPageRespVO;

import java.time.LocalDateTime;

/**
 * 访问日志服务接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 访问日志服务接口
 * @Version: 1.0.0
 */
public interface SysAccessLogService {

    /**
     * 分页查询访问日志
     *
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    PageResult<AccessLogPageRespVO> page(PageQuery<AccessLogQueryDTO> pageQuery);

    /**
     * 清理指定时间之前的访问日志
     *
     * @param beforeTime 清理该时间之前的日志
     * @return 删除条数
     */
    int clean(LocalDateTime beforeTime);
}
