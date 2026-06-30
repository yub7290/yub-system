package com.yub.system.service.log.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yub.common.model.PageQuery;
import com.yub.common.model.PageResult;
import com.yub.system.dto.log.OperaLogQueryDTO;
import com.yub.system.entity.log.SysOperaLog;
import com.yub.system.mapper.log.SysOperaLogMapper;
import com.yub.system.service.log.SysOperaLogService;
import com.yub.system.vo.log.OperaLogDetailRespVO;
import com.yub.system.vo.log.OperaLogPageRespVO;
import com.yub.framework.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志服务实现
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 操作日志服务实现
 * @Version: 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOperaLogServiceImpl implements SysOperaLogService {

    private final SysOperaLogMapper operaLogMapper;

    @Override
    public PageResult<OperaLogPageRespVO> page(PageQuery<OperaLogQueryDTO> pageQuery) {
        OperaLogQueryDTO queryParam = pageQuery.getQueryParam();
        com.yub.common.model.PageParam pageParam = pageQuery.getPageParam();

        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysOperaLog> list = operaLogMapper.selectPage(queryParam);
        PageInfo<SysOperaLog> pageInfo = new PageInfo<>(list);

        List<OperaLogPageRespVO> records = BeanUtils.copyList(list, OperaLogPageRespVO.class);
        return PageResult.of(records, pageInfo.getTotal());
    }

    @Override
    public OperaLogDetailRespVO getDetail(Long id) {
        SysOperaLog operaLog = operaLogMapper.selectById(id);
        return BeanUtils.copy(operaLog, OperaLogDetailRespVO.class);
    }

    @Override
    public int clean(LocalDateTime beforeTime) {
        int totalDeleted = 0;
        int deleted;
        // 循环删除，每次最多 10000 条，防止大事务锁表
        do {
            deleted = operaLogMapper.deleteByCreateTime(beforeTime);
            totalDeleted += deleted;
        } while (deleted > 0);
        log.info("清理操作日志完成，共删除 {} 条（保留 {} 之后的数据）", totalDeleted, beforeTime);
        return totalDeleted;
    }
}
