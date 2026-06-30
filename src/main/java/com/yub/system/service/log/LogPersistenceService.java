package com.yub.system.service.log;

import com.yub.framework.log.AccessLogEvent;
import com.yub.framework.log.OperaLogEvent;
import com.yub.system.entity.log.SysAccessLog;
import com.yub.system.entity.log.SysOperaLog;
import com.yub.system.mapper.log.SysAccessLogMapper;
import com.yub.system.mapper.log.SysOperaLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 日志持久化服务
 * <p>
 * 将内存队列中的日志事件转换为数据库实体并批量写入。
 * 此运行在单独的线程池中，不阻塞业务线程。
 * </p>
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 日志持久化服务
 * @Version: 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogPersistenceService {

    private final SysAccessLogMapper accessLogMapper;
    private final SysOperaLogMapper operaLogMapper;

    /**
     * 批量持久化访问日志
     *
     * @param events 访问日志事件列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveAccessLogs(List<AccessLogEvent> events) {
        if (events == null || events.isEmpty()) {
            return;
        }
        try {
            List<SysAccessLog> entities = events.stream()
                    .map(this::convertToAccessLog)
                    .collect(Collectors.toList());
            accessLogMapper.batchInsert(entities);
            log.debug("批量写入 {} 条访问日志", entities.size());
        } catch (Exception e) {
            log.error("批量写入访问日志失败，{} 条丢失", events.size(), e);
            // 尝试逐条回退
            fallbackInsertAccessLogs(events);
        }
    }

    /**
     * 批量持久化操作日志
     *
     * @param events 操作日志事件列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveOperaLogs(List<OperaLogEvent> events) {
        if (events == null || events.isEmpty()) {
            return;
        }
        try {
            List<SysOperaLog> entities = events.stream()
                    .map(this::convertToOperaLog)
                    .collect(Collectors.toList());
            operaLogMapper.batchInsert(entities);
            log.debug("批量写入 {} 条操作日志", entities.size());
        } catch (Exception e) {
            log.error("批量写入操作日志失败，{} 条丢失", events.size(), e);
            fallbackInsertOperaLogs(events);
        }
    }

    /**
     * 批量插入失败时回退到逐条插入（尽可能多保存）
     */
    private void fallbackInsertAccessLogs(List<AccessLogEvent> events) {
        int success = 0;
        for (AccessLogEvent event : events) {
            try {
                accessLogMapper.insert(convertToAccessLog(event));
                success++;
            } catch (Exception ex) {
                log.trace("逐条写入访问日志失败: {}", ex.getMessage());
            }
        }
        if (success > 0) {
            log.info("回退写入 {} / {} 条访问日志", success, events.size());
        }
    }

    /**
     * 批量插入失败时回退到逐条插入
     */
    private void fallbackInsertOperaLogs(List<OperaLogEvent> events) {
        int success = 0;
        for (OperaLogEvent event : events) {
            try {
                operaLogMapper.insert(convertToOperaLog(event));
                success++;
            } catch (Exception ex) {
                log.trace("逐条写入操作日志失败: {}", ex.getMessage());
            }
        }
        if (success > 0) {
            log.info("回退写入 {} / {} 条操作日志", success, events.size());
        }
    }

    // ==================== 转换方法 ====================

    private SysAccessLog convertToAccessLog(AccessLogEvent event) {
        SysAccessLog entity = new SysAccessLog();
        entity.setUserId(event.getUserId());
        entity.setNickName(event.getNickName());
        entity.setIp(event.getIp());
        entity.setMethod(event.getMethod());
        entity.setUrl(event.getUrl());
        entity.setUserAgent(event.getUserAgent());
        entity.setEventType(event.getEventType());
        entity.setStatus(event.getStatus());
        entity.setErrorMsg(event.getErrorMsg());
        entity.setCostTime(event.getCostTime());
        return entity;
    }

    private SysOperaLog convertToOperaLog(OperaLogEvent event) {
        SysOperaLog entity = new SysOperaLog();
        entity.setUserId(event.getUserId());
        entity.setNickName(event.getNickName());
        entity.setModule(event.getModule());
        entity.setOperaType(event.getOperaType());
        entity.setContent(event.getContent());
        entity.setMethodName(event.getMethodName());
        entity.setRequestParams(event.getRequestParams());
        entity.setResponseResult(event.getResponseResult());
        entity.setIp(event.getIp());
        entity.setCostTime(event.getCostTime());
        return entity;
    }
}
