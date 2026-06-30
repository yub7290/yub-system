package com.yub.system.service.log.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.system.dto.log.AccessLogQueryDTO;
import com.yub.system.entity.log.SysAccessLog;
import com.yub.system.mapper.log.SysAccessLogMapper;
import com.yub.system.service.log.SysAccessLogService;
import com.yub.system.vo.log.AccessLogPageRespVO;
import com.yub.framework.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 访问日志服务实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 访问日志服务实现
 * @Version: 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysAccessLogServiceImpl implements SysAccessLogService {

    private final SysAccessLogMapper accessLogMapper;

    @Override
    public PageResult<AccessLogPageRespVO> page(PageQuery<AccessLogQueryDTO> pageQuery) {
        AccessLogQueryDTO queryParam = pageQuery.getQueryParam();
        com.yub.common.model.PageParam pageParam = pageQuery.getPageParam();

        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysAccessLog> list = accessLogMapper.selectPage(queryParam);
        PageInfo<SysAccessLog> pageInfo = new PageInfo<>(list);

        List<AccessLogPageRespVO> records = BeanUtils.copyList(list, AccessLogPageRespVO.class);
        return PageResult.of(records, pageInfo.getTotal());
    }

    @Override
    public int clean(LocalDateTime beforeTime) {
        int totalDeleted = 0;
        int deleted;
        // 循环删除，每次最多 10000 条，防止大事务锁表
        do {
            deleted = accessLogMapper.deleteByCreateTime(beforeTime);
            totalDeleted += deleted;
        } while (deleted > 0);
        log.info("清理访问日志完成，共删除 {} 条（保留 {} 之后的数据）", totalDeleted, beforeTime);
        return totalDeleted;
    }
}
