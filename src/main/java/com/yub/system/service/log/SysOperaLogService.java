package com.yub.system.service.log;

import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.system.dto.log.OperaLogQueryDTO;
import com.yub.system.vo.log.OperaLogDetailRespVO;
import com.yub.system.vo.log.OperaLogPageRespVO;

import java.time.LocalDateTime;

/**
 * 操作日志服务接口
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 操作日志服务接口
 * @Version: 1.0.0
 */
public interface SysOperaLogService {

    /**
     * 分页查询操作日志
     *
     * @param pageQuery 分页查询参数
     * @return 分页结果
     */
    PageResult<OperaLogPageRespVO> page(PageQuery<OperaLogQueryDTO> pageQuery);

    /**
     * 获取操作日志详情
     *
     * @param id 日志ID
     * @return 日志详情
     */
    OperaLogDetailRespVO getDetail(Long id);

    /**
     * 清理指定时间之前的操作日志
     *
     * @param beforeTime 清理该时间之前的日志
     * @return 删除条数
     */
    int clean(LocalDateTime beforeTime);
}
