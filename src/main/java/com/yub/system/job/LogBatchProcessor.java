package com.yub.system.job;

import com.yub.framework.log.AccessLogEvent;
import com.yub.framework.log.LogBuffer;
import com.yub.framework.log.OperaLogEvent;
import com.yub.system.service.log.LogPersistenceService;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 日志批量写入处理器
 * <p>
 * 定时从 LogBuffer 中 drain 日志事件，批量写入数据库。
 * 触发条件：每 3 秒定时执行 或 队列积压超过阈值。
 * 应用关闭时尝试排空剩余日志（最多等待 5 秒）。
 * </p>
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 日志批量写入处理器
 * @Version: 1.0.0
 */
@Slf4j
@Component
public class LogBatchProcessor {

    private final LogBuffer logBuffer;
    private final LogPersistenceService persistenceService;

    /** 单次最大批量写入条数 */
    @Value("${log.batch.max-size:200}")
    private int maxBatchSize;

    /** 触发批量写入的队列积压阈值 */
    @Value("${log.batch.threshold:500}")
    private int batchThreshold;

    /** 应用关闭时排空等待超时（秒） */
    @Value("${log.batch.shutdown-timeout:5}")
    private int shutdownTimeout;

    /** 是否正在关闭 */
    private final AtomicBoolean shuttingDown = new AtomicBoolean(false);

    public LogBatchProcessor(LogBuffer logBuffer, LogPersistenceService persistenceService) {
        this.logBuffer = logBuffer;
        this.persistenceService = persistenceService;
    }

    /**
     * 定时批量写入：每 3 秒执行一次
     */
    @Scheduled(fixedRateString = "${log.batch.interval:3000}")
    public void processAccessLogs() {
        if (shuttingDown.get()) {
            return;
        }
        // 检查积压量是否超过阈值，未超过且不为定时触发时跳过
        int accessQueueSize = logBuffer.getAccessLogQueueSize();
        int operaQueueSize = logBuffer.getOperaLogQueueSize();

        if (accessQueueSize > 0) {
            processAccessBatch();
        }
        if (operaQueueSize > 0) {
            processOperaBatch();
        }
    }

    /**
     * 处理一批访问日志
     */
    private void processAccessBatch() {
        List<AccessLogEvent> batch = logBuffer.drainAccessLogs(maxBatchSize);
        if (!batch.isEmpty()) {
            persistenceService.batchSaveAccessLogs(batch);
        }
    }

    /**
     * 处理一批操作日志
     */
    private void processOperaBatch() {
        List<OperaLogEvent> batch = logBuffer.drainOperaLogs(maxBatchSize);
        if (!batch.isEmpty()) {
            persistenceService.batchSaveOperaLogs(batch);
        }
    }

    /**
     * 应用关闭时排空剩余日志
     */
    @PreDestroy
    public void flushOnShutdown() {
        shuttingDown.set(true);
        log.info("准备排空日志缓冲区（等待最多 {} 秒）...", shutdownTimeout);

        long deadline = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(shutdownTimeout);
        int totalFlushed = 0;

        while (System.currentTimeMillis() < deadline) {
            int accessSize = logBuffer.getAccessLogQueueSize();
            int operaSize = logBuffer.getOperaLogQueueSize();
            if (accessSize == 0 && operaSize == 0) {
                break;
            }

            processAccessBatch();
            processOperaBatch();
            totalFlushed += Math.min(accessSize, maxBatchSize) + Math.min(operaSize, maxBatchSize);

            if (logBuffer.getAccessLogQueueSize() > 0 || logBuffer.getOperaLogQueueSize() > 0) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        log.info("日志缓冲区排空完成，共处理 {} 条", totalFlushed);

        int remaining = logBuffer.getAccessLogQueueSize() + logBuffer.getOperaLogQueueSize();
        if (remaining > 0) {
            log.warn("日志缓冲区仍有 {} 条未写入（超时丢弃）", remaining);
        }
    }
}
